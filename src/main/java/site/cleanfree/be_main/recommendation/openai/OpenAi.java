package site.cleanfree.be_main.recommendation.openai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import site.cleanfree.be_main.recommendation.domain.Solution;

@Slf4j
@Component
public class OpenAi {

    @Value("${openAiApiKey}")
    private String openAiApiKey;

    @Value("${gptPrompt}")
    private String prompt;

    public Mono<GptResponseDto> getGptResponse(String question) {
        WebClient webClient = WebClient.create("https://api.openai.com");
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> systemMessages = setSystemMessages();

        Map<String, Object> userMessages = new HashMap<>();
        userMessages.put("role", "user");
        userMessages.put("content", question);

        body.put("model", "gpt-3.5-turbo-0125");
        body.put("messages", List.of(systemMessages, userMessages));
        return webClient.post()
            .uri(uriBuilder -> uriBuilder.path("/v1/chat/completions").build())
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", openAiApiKey)
            .body(BodyInserters.fromValue(body))
            .retrieve()
            .bodyToMono(String.class)
            .flatMap(this::parseJsonStringToGptResponseDto)
            .onErrorReturn(new GptResponseDto());
    }

    private Map<String, Object> setSystemMessages() {
        Map<String, Object> systemMessages = new HashMap<>();
        systemMessages.put("role", "system");
        systemMessages.put("content", prompt);
        return systemMessages;
    }

    private Mono<GptResponseDto> parseJsonStringToGptResponseDto(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode contentText = rootNode.path("choices").path(0).path("message").path("content");
            JsonNode content = objectMapper.readTree(contentText.asText());

            List<String> ingredients = objectMapper.convertValue(
                content.get("ingredients"),
                new TypeReference<List<String>>() {
                }
            );

            List<Solution> solutions = objectMapper.convertValue(
                content.get("solutions"),
                new TypeReference<List<Solution>>() {
                }
            );

//            log.info("ingredients: {},\nsolutions: {}", ingredients, solutions);

            return Mono.just(GptResponseDto.builder()
                .ingredients(ingredients)
                .solutions(solutions)
                .build());
        } catch (IOException e) {
            return Mono.error(new RuntimeException("Error parsing response: " + e.getMessage()));
        }
    }
}
