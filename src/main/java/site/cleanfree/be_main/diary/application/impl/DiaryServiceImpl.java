package site.cleanfree.be_main.diary.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.diary.application.DiaryService;
import site.cleanfree.be_main.diary.dto.GetDiaryListDto;
import site.cleanfree.be_main.diary.infrastructure.DiaryRepository;
import site.cleanfree.be_main.security.JwtTokenProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 최신순으로 피부 일지 리스트 조회
    @Override
    public BaseResponse<List<GetDiaryListDto>> getDiaryList(String token) {
        String uuid = jwtTokenProvider.getUuid(token);

        return BaseResponse.successResponse("Diary List", diaryRepository.findAllByMemberUuidOrderByCreatedAt(uuid));
    }
}
