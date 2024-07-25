package site.cleanfree.be_main.diary.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
import site.cleanfree.be_main.diary.infrastructure.DiaryRepository;
import site.cleanfree.be_main.security.JwtTokenProvider;

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
                    .writeTime(TimeConvertor.utcToKst(diaryWriteRequestDto.getWriteTime()))
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
                .writeTime(TimeConvertor.utcToKst(diaryUpdateRequestDto.getWriteTime()))
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
}
