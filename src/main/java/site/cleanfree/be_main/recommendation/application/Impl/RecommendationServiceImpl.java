package site.cleanfree.be_main.recommendation.application.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.UuidProvider;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.recommendation.application.RecommendationService;
import site.cleanfree.be_main.recommendation.domain.Recommendation;
import site.cleanfree.be_main.recommendation.dto.ResultListResponseDto;
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

        if (question == null || question.isEmpty()) {
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
                .errorCode(null)
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
            .errorCode(null)
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

    public BaseResponse<List<ResultListResponseDto>> getResults(String authorization) {
        String memberUuid = jwtTokenProvider.getUuid(authorization);
        List<Recommendation> recommendations = recommendationRepository.getAllByMemberUuid(
            memberUuid);

        List<ResultListResponseDto> resultListResponseDtos = recommendations.stream().map(
            recommendation -> ResultListResponseDto.builder()
                .resultId(recommendation.getResultId())
                .dayDifference(getDayDifference(recommendation.getCreatedAt()))
                .isAnalyze(recommendation.getIsAnalyze())
                .build()).toList();

        return BaseResponse.<List<ResultListResponseDto>>builder()
            .success(true)
            .errorCode(null)
            .message("result list find success")
            .data(resultListResponseDtos)
            .build();
    }

    private String getDayDifference(LocalDateTime createdAt) {
        log.info("createdAt >>> {}", createdAt.toString());

        LocalDateTime kstDateTime = createdAt.atZone(ZoneId.of("UTC"))
            .withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        // 날짜 부분만 가져오기
        LocalDate createdDate = createdAt.toLocalDate();

        // 현재 시간 가져오기 (KST)
        LocalDate nowKST = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // writeTime과 현재 시간의 차이 계산
        long daysBetween = ChronoUnit.DAYS.between(createdDate, nowKST);
        long monthsBetween = ChronoUnit.MONTHS.between(createdDate, nowKST);
        long yearsBetween = ChronoUnit.YEARS.between(createdDate, nowKST);
        log.info("years between >>> {}, months between >>> {}, days between >>> {}",
            yearsBetween, monthsBetween, daysBetween);

        if (yearsBetween > 0) {
            return String.format("%d년 전", yearsBetween);
        } else if (monthsBetween > 0) {
            return String.format("%d개월 전", monthsBetween);
        } else if (daysBetween > 0) {
            return String.format("%d일 전", daysBetween);
        }

        return "오늘";
    }
}
