package site.cleanfree.be_main.recommendation.application.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.auth.domain.Member;
import site.cleanfree.be_main.auth.infrastructure.MemberRepository;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.UuidProvider;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.recommendation.application.RecommendationService;
import site.cleanfree.be_main.recommendation.domain.Recommendation;
import site.cleanfree.be_main.recommendation.dto.ResultListResponseDto;
import site.cleanfree.be_main.recommendation.dto.ResultSimpleResponseDto;
import site.cleanfree.be_main.recommendation.dto.ResultResponseDto;
import site.cleanfree.be_main.recommendation.infrastructure.RecommendationRepository;
import site.cleanfree.be_main.recommendation.vo.QuestionVo;
import site.cleanfree.be_main.security.JwtTokenProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final MemberRepository memberRepository;
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

    public BaseResponse<ResultListResponseDto> getResults(String authorization) {
        String memberUuid = jwtTokenProvider.getUuid(authorization);
        List<Recommendation> recommendations = recommendationRepository.getAllByMemberUuid(
            memberUuid);

        List<ResultSimpleResponseDto> resultSimpleResponseDtos = recommendations.stream().map(
            recommendation -> ResultSimpleResponseDto.builder()
                .resultId(recommendation.getResultId())
                .dayDifference(getDayDifference(recommendation.getCreatedAt()))
                .isAnalyze(recommendation.getIsAnalyze())
                .build()).toList();

        Integer todayAccessCount = addDayAccessCount(memberUuid);

        return BaseResponse.<ResultListResponseDto>builder()
            .success(true)
            .errorCode(null)
            .message("result list find success")
            .data(ResultListResponseDto.builder()
                .results(resultSimpleResponseDtos)
                .dayAccessCount(todayAccessCount)
                .build())
            .build();
    }

    private String getDayDifference(LocalDateTime createdAt) {

        LocalDateTime kstDateTime = createdAt.atZone(ZoneId.of("UTC"))
            .withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        LocalDate kstDate = kstDateTime.toLocalDate();

        // 현재 시간 (KST)
        LocalDateTime nowKstDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDate nowKSTDate = LocalDate.now(ZoneId.of("Asia/Seoul"));

        long hoursBetween = ChronoUnit.HOURS.between(kstDateTime, nowKstDateTime);
        long daysBetween = ChronoUnit.DAYS.between(kstDate, nowKSTDate);
        long monthsBetween = ChronoUnit.MONTHS.between(kstDate, nowKSTDate);
        long yearsBetween = ChronoUnit.YEARS.between(kstDate, nowKSTDate);
        log.info("years: {}, months: {}, days: {}, hours: {}",
            yearsBetween, monthsBetween, daysBetween, hoursBetween);

        if (yearsBetween > 0) {
            return String.format("%d년 전", yearsBetween);
        } else if (monthsBetween > 0) {
            return String.format("%d개월 전", monthsBetween);
        } else if (daysBetween > 0) {
            return String.format("%d일 전", daysBetween);
        } else if (hoursBetween > 0) {
            return String.format("%d시간 전", hoursBetween);
        }

        return "방금 전";
    }

    private Integer addDayAccessCount(String memberUuid) {
        Optional<Member> memberOpt = memberRepository.findByUuid(memberUuid);

        if (memberOpt.isEmpty()) {
            return null;
        }

        Member member = memberOpt.get();
        Integer todayAccessCount = member.getDayAccessCount();

        if (!isKstToday(member.getUpdatedAt())) {
            try {
                memberRepository.save(Member.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .name(member.getName())
                    .gender(member.getGender())
                    .uuid(member.getUuid())
                    .birthDate(member.getBirthDate())
                    .dayAccessCount(todayAccessCount + 1)
                    .build());
                return todayAccessCount;
            } catch (Exception e) {
                log.info("add dayAccessCount fail: {}", e.getMessage());
                return null;
            }
        }
        return todayAccessCount;
    }

    private boolean isKstToday(LocalDateTime utcDateTime) {
        LocalDate kstDate = utcDateTime.atZone(ZoneId.of("UTC"))
            .withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDate();

        LocalDate kstToday = LocalDateTime.now().atZone(ZoneId.of("UTC"))
            .withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDate();

        if (kstDate.equals(kstToday)) {
            return true;
        }
        return false;
    }
}
