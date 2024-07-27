package site.cleanfree.be_main.diary.application;

import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.diary.dto.DiaryResponseDto;
import site.cleanfree.be_main.diary.dto.DiaryUpdateRequestDto;
import site.cleanfree.be_main.diary.dto.DiaryWriteRequestDto;
import site.cleanfree.be_main.diary.dto.GetDiaryListDto;

import java.util.List;
import site.cleanfree.be_main.diary.dto.RecentCosmeticsResponseDto;

@Service
public interface DiaryService {

    BaseResponse<?> writeDiary(String token, DiaryWriteRequestDto diaryWriteRequestDto);

    BaseResponse<?> updateDiary(String token, DiaryUpdateRequestDto diaryUpdateRequestDto);

    BaseResponse<DiaryResponseDto> getDiaryById(String token, String diaryId);

    BaseResponse<List<GetDiaryListDto>> getDiaryList(String token);

    BaseResponse<RecentCosmeticsResponseDto> getRecentDiaryById(String token);
}
