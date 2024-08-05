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
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import site.cleanfree.be_main.auth.domain.Member;
import site.cleanfree.be_main.auth.infrastructure.MemberRepository;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.UuidProvider;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.recommendation.domain.Reference;
import site.cleanfree.be_main.recommendation.infrastructure.ReactiveRecommendationRepository;
import site.cleanfree.be_main.recommendation.openai.GptResponseDto;
import site.cleanfree.be_main.recommendation.openai.OpenAi;
import site.cleanfree.be_main.recommendation.status.SearchLimit;
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
    private final ReactiveRecommendationRepository reactiveRecommendationRepository;
    private final OpenAi openAi;

    @Override
    @Transactional
    public BaseResponse<?> search(String authorization, QuestionVo questionVo) {
        String resultId = UuidProvider.generateRecommendationResultId();
        String memberUuid = jwtTokenProvider.getUuid(authorization);
        String question = questionVo.getQuestion();

        Optional<Member> memberOpt = memberRepository.findByUuid(memberUuid);

        if (memberOpt.isEmpty()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.NOT_EXISTED_MEMBER.getCode())
                .message("not existed member")
                .data(null)
                .build();
        }

        if (question == null || question.isEmpty()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("request question not exist")
                .data(null)
                .build();
        }

        Member member = memberOpt.get();

        if (isOverSearchCount(member)) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.SEARCH_LIMIT_REACHED.getCode())
                .message("question has reached the daily limit.")
                .data(null)
                .build();
        }

        try {
            memberRepository.save(Member.converter(member, member.getSearchCount() + 1));

            Mono<GptResponseDto> gptResponseMono = openAi.getGptResponse(question);

            // Recommendation 객체 저장을 비동기적으로 실행
            gptResponseMono.flatMap(gptResponseDto -> {
                    Recommendation recommendation = Recommendation.builder()
                        .resultId(resultId)
                        .memberUuid(memberUuid)
                        .question(questionVo.getQuestion())
                        .references(new Reference())
                        .ingredients(gptResponseDto.getIngredients())
                        .solutions(gptResponseDto.getSolutions())
                        .isAnalyze(false)
                        .build();

                    return reactiveRecommendationRepository.save(recommendation);
                })
                .doOnSuccess(savedRecommendation -> {
                    log.info("Recommendation saved successfully");
                })
                .doOnError(e -> {
                    log.error("Recommendation save failed because {}", e.getMessage());
                })
                .subscribe(); // 저장 작업을 비동기적으로 실행
            return BaseResponse.builder()
                .success(true)
                .errorCode(null)
                .message("question save success")
                .data(null)
                .build();
        } catch (Exception e) {
            log.info("question save fail because {}", e.getMessage());
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("question save fail")
                .data(null)
                .build();
        }
    }

    private boolean isOverSearchCount(Member member) {
        Recommendation recentSearch = getRecentSearch(member.getUuid());

        if (recentSearch == null) {
            return false;
        }

        LocalDateTime recentSearchCreatedAt = recentSearch.getCreatedAt();
        LocalDate kstRecentCreatedAtDate = recentSearchCreatedAt.atZone(ZoneId.of("UTC"))
            .withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDate();

        LocalDate nowKstDate = LocalDate.now(ZoneId.of("Asia/Seoul"));

        if (kstRecentCreatedAtDate.isBefore(nowKstDate)) {
            memberRepository.save(Member.converter(member, 0));
            return false;
        } else
            return member.getSearchCount() >= SearchLimit.ONE_PER_DAY.getCount();
    }

    private Recommendation getRecentSearch(String memberUuid) {
        return recommendationRepository.findTopByMemberUuidOrderByCreatedAtDesc(memberUuid).orElse(null);
    }

    @Override
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
                    .answer(recommendationResult.getAnswer())
                    .cosmetics(recommendationResult.getCosmetics())
                    .ingredients(recommendationResult.getIngredients())
                    .solutions(recommendationResult.getSolutions())
                    .references(recommendationResult.getReferences())
                    .isAnalyze(recommendationResult.getIsAnalyze())
                    .build()
            )
            .build();
    }

    private Recommendation getRecommendationByResultId(String resultId) {
        return recommendationRepository.getRecommendationByResultId(resultId).orElse(null);
    }

    @Override
    public BaseResponse<ResultListResponseDto> getResults(String authorization) {
        String memberUuid = jwtTokenProvider.getUuid(authorization);
        List<Recommendation> recommendations = recommendationRepository.getAllByMemberUuid(
            memberUuid);

        List<ResultSimpleResponseDto> resultSimpleResponseDtos = recommendations.stream().map(
            recommendation -> ResultSimpleResponseDto.builder()
                .resultId(recommendation.getResultId())
                .question(recommendation.getQuestion())
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

        return kstDate.equals(kstToday);
    }

    @Override
    public BaseResponse<Integer> getSearchCount(String authorization) {
        String memberUuid = jwtTokenProvider.getUuid(authorization);
        Optional<Member> memberOpt = memberRepository.findByUuid(memberUuid);

        if (memberOpt.isEmpty()) {
            return BaseResponse.<Integer>builder()
                .success(false)
                .errorCode(ErrorStatus.NOT_EXISTED_MEMBER.getCode())
                .message("member not exist")
                .data(null)
                .build();
        }

        Integer searchCount = memberOpt.get().getSearchCount();

        return BaseResponse.<Integer>builder()
            .success(true)
            .errorCode(null)
            .message("find search count limit success")
            .data(searchCount)
            .build();
    }
}
