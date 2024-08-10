package site.cleanfree.be_main.createvalue.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.createvalue.application.CreatevalueService;
import site.cleanfree.be_main.createvalue.vo.CreatevalueSearchRequestVo;

@RestController
@RequestMapping("/api/v1/createvalue")
@RequiredArgsConstructor
@Tag(name = "createvalue API", description = "자기 능력으로 돈벌이 방법 찾기 API")
public class CreatevalueController {

    private final CreatevalueService createvalueService;

    @PostMapping("/search")
    @Operation(summary = "검색 API", description = "검색어를 저장합니다.")
    public ResponseEntity<BaseResponse<Object>> search(
        @RequestBody CreatevalueSearchRequestVo createvalueSearchRequestVo
    ) {
        return ResponseEntity.ok(createvalueService.search(createvalueSearchRequestVo));
    }
}
