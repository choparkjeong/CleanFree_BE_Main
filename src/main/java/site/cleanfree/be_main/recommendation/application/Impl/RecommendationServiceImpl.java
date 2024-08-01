package site.cleanfree.be_main.recommendation.application.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.UuidProvider;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.recommendation.application.RecommendationService;
import site.cleanfree.be_main.recommendation.domain.Recommendation;
import site.cleanfree.be_main.recommendation.dto.ResultResponseDto;
import site.cleanfree.be_main.recommendation.infrastructure.RecommendationRepository;
import site.cleanfree.be_main.recommendation.vo.QuestionVo;
import site.cleanfree.be_main.security.JwtTokenProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public BaseResponse<?> search(String authorization, QuestionVo questionVo) {
        String resultId = UuidProvider.generateRecommendationResultId();
        String memberUuid = jwtTokenProvider.getUuid(authorization);
        String question = questionVo.getQuestion();

        if (question.isEmpty() || question == null) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("request question not exist")
                .data(null)
                .build();
        }

        try {
            recommendationRepository.save(Recommendation.builder()
                .resultId(resultId)
                .memberUuid(memberUuid)
                .question(questionVo.getQuestion())
                .isAnalyze(false)
                .build());
            log.info("question saved");
            return BaseResponse.builder()
                .success(true)
                .errorCode(ErrorStatus.SUCCESS.getCode())
                .message("question save success")
                .data(null)
                .build();
        } catch (Exception e) {
            log.info("question save fail because of {}", e.getMessage());
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("question save fail")
                .data(null)
                .build();
        }
    }

    public BaseResponse<ResultResponseDto> getResult(String authorization, String resultId) {
        Recommendation recommendationResult = getRecommendationByResultId(resultId);

        if (recommendationResult == null) {
            log.info("resultId not exist");
            return BaseResponse.<ResultResponseDto>builder()
                .success(true)
                .errorCode(ErrorStatus.NOT_EXISTED_ERROR.getCode())
                .message("resultId not exist")
                .data(null)
                .build();
        }
        log.info("resultId exist");
        return BaseResponse.<ResultResponseDto>builder()
            .success(true)
            .errorCode(ErrorStatus.SUCCESS.getCode())
            .message("recommendation result find success")
            .data(
                ResultResponseDto.builder()
                    .resultId(recommendationResult.getResultId())
                    .memberUuid(recommendationResult.getMemberUuid())
                    .question(recommendationResult.getQuestion())
                    .cosmetics(recommendationResult.getCosmetics())
                    .ingredients(recommendationResult.getIngredients())
                    .references(recommendationResult.getReferences())
                    .isAnalyze(recommendationResult.getIsAnalyze())
                    .build()
            )
            .build();
    }

    private Recommendation getRecommendationByResultId(String resultId) {
        return recommendationRepository.getRecommendationByResultId(resultId).orElse(null);
    }
}
