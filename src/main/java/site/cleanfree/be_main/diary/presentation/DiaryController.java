package site.cleanfree.be_main.diary.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.diary.application.DiaryService;
import site.cleanfree.be_main.diary.dto.DiaryResponseDto;
import site.cleanfree.be_main.diary.dto.DiaryUpdateRequestDto;
import site.cleanfree.be_main.diary.dto.DiaryWriteRequestDto;
import site.cleanfree.be_main.diary.dto.GetDiaryListDto;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "피부 일지 API", description = "피부 일지 API")
@RequestMapping("/api/v1/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<DiaryResponseDto>> getDiaryById(
        @RequestHeader String Authorization,
        @PathVariable(value = "id") String id
    ) {
        return ResponseEntity.ok(diaryService.getDiaryById(Authorization, id));
    }

    @PostMapping("/write")
    public ResponseEntity<BaseResponse<?>> writeDiary(
        @RequestHeader String Authorization,
        @RequestBody DiaryWriteRequestDto diaryWriteRequestDto
    ) {
        return ResponseEntity.ok(diaryService.writeDiary(Authorization, diaryWriteRequestDto));
    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse<?>> updateDiary(
        @RequestHeader String Authorization,
        @RequestBody DiaryUpdateRequestDto diaryUpdateRequestDto
    ) {
        return ResponseEntity.ok(diaryService.updateDiary(Authorization, diaryUpdateRequestDto));
    }

    @GetMapping("/list")
    @Operation(summary = "피부 일지 리스트 조회 API", description = "피부 일지 리스트 조회 API")
    public ResponseEntity<BaseResponse<List<GetDiaryListDto>>> getDiaryList(
            @RequestHeader String Authorization
    ) {
        return ResponseEntity.ok()
                .body(diaryService.getDiaryList(Authorization));
    }

    @GetMapping("/recent")
    @Operation(summary = "최근 일지 조회 API", description = "최근 일지를 조회합니다. 없으면 null, 빈리스트로 응답합니다.")
    public ResponseEntity<BaseResponse<DiaryResponseDto>> getRecentDiary(
        @RequestHeader String Authorization
    ) {
        log.info("Authorization: {}", Authorization);
        return ResponseEntity.ok(diaryService.getRecentDiaryById(Authorization));
    }
}
