package site.cleanfree.be_main.diary.application;

import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.diary.dto.GetDiaryListDto;

import java.util.List;

public interface DiaryService {
    BaseResponse<List<GetDiaryListDto>> getDiaryList(String token);
}
