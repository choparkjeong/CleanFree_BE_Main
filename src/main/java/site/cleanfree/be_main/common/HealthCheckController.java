package site.cleanfree.be_main.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import site.cleanfree.be_main.recommendation.openai.GptResponseDto;
import site.cleanfree.be_main.recommendation.openai.OpenAi;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/health-check")
public class HealthCheckController {

    private final OpenAi openAi;

    @GetMapping
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok(true);
    }

//    @PostMapping("/gpt-async")
//    public Mono<GptResponseDto> openAiAsyncTest(@RequestBody String question) {
//        return openAi.getGptResponse(question);
//    }
}
