package site.cleanfree.be_main.diary.application.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.TimeConvertor;
import site.cleanfree.be_main.common.UuidProvider;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.diary.application.DiaryService;
import site.cleanfree.be_main.diary.domain.Diary;
import site.cleanfree.be_main.diary.dto.DiaryUpdateRequestDto;
import site.cleanfree.be_main.diary.dto.DiaryWriteRequestDto;
import site.cleanfree.be_main.diary.dto.DiaryResponseDto;
import site.cleanfree.be_main.diary.dto.GetDiaryListDto;
import site.cleanfree.be_main.diary.infrastructure.DiaryRepository;
import site.cleanfree.be_main.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import static site.cleanfree.be_main.common.TimeConvertor.convertWriteTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryServiceImpl implements DiaryService {

    private final JwtTokenProvider jwtTokenProvider;
    private final DiaryRepository diaryRepository;

    private String getMemberUuid(String token) {
        return jwtTokenProvider.getUuid(token);
    }

    @Override
    public BaseResponse<?> writeDiary(String token, DiaryWriteRequestDto diaryWriteRequestDto) {
        try {
            diaryRepository.save(
                Diary.builder()
                    .diaryId(UuidProvider.generateDiaryUuid())
                    .memberUuid(getMemberUuid(token))
                    .skinStatus(diaryWriteRequestDto.getSkinStatus())
                    .thumbnailUrl(diaryWriteRequestDto.getThumbnailUrl())
                    .cosmetics(diaryWriteRequestDto.getCosmetics())
                    .isAlcohol(diaryWriteRequestDto.isAlcohol())
                    .isExercise(diaryWriteRequestDto.isExercise())
                    .sleepTime(diaryWriteRequestDto.getSleepTime())
                    .memo(diaryWriteRequestDto.getMemo())
                    .writeTime(TimeConvertor.writeTimeToDateTime(diaryWriteRequestDto.getWriteTime()))
                    .build()
            );

            return BaseResponse.builder()
                .success(true)
                .errorCode(ErrorStatus.SUCCESS.getCode())
                .message("Diary saved.")
                .data(null)
                .build();
        } catch (Exception exception) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("Diary not saved.")
                .data(null)
                .build();
        }
    }

    @Override
    public BaseResponse<?> updateDiary(String token, DiaryUpdateRequestDto diaryUpdateRequestDto) {
        String memberUuid = getMemberUuid(token);
        Diary diary = findDiaryByMemberUuidAndDiaryId(
            memberUuid,
            diaryUpdateRequestDto.getDiaryId()
        );

        if (diary == null) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.NOT_EXISTED_ERROR.getCode())
                .message("Diary not exist.")
                .data(null)
                .build();
        }
        try {


        diaryRepository.save(
            Diary.builder()
                .id(diary.getId())
                .diaryId(diaryUpdateRequestDto.getDiaryId())
                .memberUuid(memberUuid)
                .skinStatus(diaryUpdateRequestDto.getSkinStatus())
                .thumbnailUrl(diaryUpdateRequestDto.getThumbnailUrl())
                .cosmetics(diaryUpdateRequestDto.getCosmetics())
                .isAlcohol(diaryUpdateRequestDto.isAlcohol())
                .isExercise(diaryUpdateRequestDto.isExercise())
                .sleepTime(diaryUpdateRequestDto.getSleepTime())
                .memo(diaryUpdateRequestDto.getMemo())
                .writeTime(diaryUpdateRequestDto.getWriteTime())
                .build()
        );

        return BaseResponse.builder()
            .success(true)
            .errorCode(ErrorStatus.SUCCESS.getCode())
            .message("Diary update success.")
            .data(null)
            .build();
        } catch(Exception e) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_UPDATE_ERROR.getCode())
                .message("Diary update fail.")
                .data(null)
                .build();
        }
    }

    private Diary findDiaryByMemberUuidAndDiaryId(String memberUuid, String diaryId) {
        return diaryRepository.getDiaryByMemberUuidAndDiaryId(memberUuid, diaryId)
            .orElse(null);
    }

    @Override
    public BaseResponse<DiaryResponseDto> getDiaryById(String token, String diaryId) {
        String memberUuid = getMemberUuid(token);
        Diary diary = findDiaryByMemberUuidAndDiaryId(memberUuid, diaryId);

        log.info("diary: {}", diary);
        if (diary == null) {
            return BaseResponse.<DiaryResponseDto>builder()
                .success(true)
                .errorCode(ErrorStatus.SUCCESS.getCode())
                .message("Not exist diary")
                .data(null)
                .build();
        }

        return BaseResponse.<DiaryResponseDto>builder()
            .success(true)
            .errorCode(ErrorStatus.SUCCESS.getCode())
            .message("Find diary success")
            .data(DiaryResponseDto.builder()
                .diaryId(diaryId)
                .skinStatus(diary.getSkinStatus())
                .thumbnailUrl(diary.getThumbnailUrl())
                .cosmetics(diary.getCosmetics())
                .isAlcohol(diary.isAlcohol())
                .isExercise(diary.isExercise())
                .sleepTime(diary.getSleepTime())
                .memo(diary.getMemo())
                .writeTime(diary.getWriteTime())
                .build())
            .build();
    }

    @Override
    public BaseResponse<List<GetDiaryListDto>> getDiaryList(String token) {
        String uuid = jwtTokenProvider.getUuid(token);

        List<GetDiaryListDto> getDiaryListDtoList = diaryRepository.findAllByMemberUuidOrderByWriteTimeDesc(uuid);

        // 해당 리스트 들고와서 하나씩 처리
        for(GetDiaryListDto getDiaryListDto : getDiaryListDtoList) {
            String dayDifference = getDayDifference(getDiaryListDto.getWriteTime());
            getDiaryListDto.setDayDifference(dayDifference);
            // 반환 시, writeTime 형식 변환
            getDiaryListDto.setWriteTime(convertWriteTime(getDiaryListDto.getWriteTime()));
        }

        return BaseResponse.successResponse(
                "Find diary List success", getDiaryListDtoList);
    }

    // dayDifference 생성 메서드
    private String getDayDifference(String writeTime) {
        // DB에서 받아온 시간 형식에 맞춰서 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss 'UTC' yyyy", Locale.ENGLISH);
        LocalDateTime writeDateTime = LocalDateTime.parse(writeTime, formatter);
        log.info("writeDateTime >>> {}", writeDateTime.toString());

        // 현재 시간 가져오기 (KST)
        LocalDateTime nowKST = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        log.info("nowKST >>> {}", nowKST.toString());

        // writeTime과 현재 시간의 차이 계산
        long daysBetween = ChronoUnit.DAYS.between(writeDateTime, nowKST);
        long monthsBetween = ChronoUnit.MONTHS.between(writeDateTime, nowKST);
        long yearsBetween = ChronoUnit.YEARS.between(writeDateTime, nowKST);
        log.info("yearsBetween >>> {}, monthsBetween >>> {}, daysBetween >>> {}",
                yearsBetween, monthsBetween, daysBetween);

        // 1년 이상된 경우 처리
        if(yearsBetween > 0) {
            return String.format("%d년 전", yearsBetween);
        }

        // 1개월 이상인 경우
        if(monthsBetween > 0) {
            return String.format("%d개월 전", monthsBetween);
        }

        // 1개월 미만인 경우
        if(daysBetween > 0) {
            return String.format("%d일 전", daysBetween);
        }

        // 오늘인 경우
        return "오늘";
    }

    @Override
    public BaseResponse<DiaryResponseDto> getRecentDiaryById(String token) {
        String memberUuid = getMemberUuid(token);
        Optional<Diary> diaryOpt = diaryRepository.findTopByMemberUuidOrderByWriteTimeDesc(memberUuid);

        if (diaryOpt.isEmpty()) {
            return BaseResponse.<DiaryResponseDto>builder()
                .success(true)
                .errorCode(ErrorStatus.SUCCESS.getCode())
                .message("Not exist diary")
                .data(null)
                .build();
        }
        Diary diary = diaryOpt.get();
        return BaseResponse.<DiaryResponseDto>builder()
            .success(true)
            .errorCode(ErrorStatus.SUCCESS.getCode())
            .message("Find Recent diary success")
            .data(DiaryResponseDto.builder()
                .diaryId(diary.getDiaryId())
                .skinStatus(diary.getSkinStatus())
                .thumbnailUrl(diary.getThumbnailUrl())
                .cosmetics(diary.getCosmetics())
                .isAlcohol(diary.isAlcohol())
                .isExercise(diary.isExercise())
                .sleepTime(diary.getSleepTime())
                .memo(diary.getMemo())
                .writeTime(diary.getWriteTime())
                .build())
            .build();
    }
}
