package site.cleanfree.be_main.diary.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.diary.application.DiaryService;
import site.cleanfree.be_main.diary.dto.GetDiaryListDto;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "피부 일지 API", description = "피부 일지 API")
@RequestMapping("/api/v1/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/diary-list")
    @Operation(summary = "피부 일지 리스트 조회 API", description = "피부 일지 리스트 조회 API")
    public ResponseEntity<BaseResponse<List<GetDiaryListDto>>> getDiaryList(
            @RequestHeader String token
    ) {
        return ResponseEntity.ok()
                .body(diaryService.getDiaryList(token));
    }
}
