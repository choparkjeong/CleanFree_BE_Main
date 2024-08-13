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
import site.cleanfree.be_main.createvalue.vo.CreatevalueRegisterRequestVo;

@RestController
@RequestMapping("/api/v1/createvalue")
@RequiredArgsConstructor
@Tag(name = "createvalue API", description = "인플루언서 수익창출 방법 찾아주기  API")
public class CreatevalueController {

    private final CreatevalueService createvalueService;

    @PostMapping("/register")
    @Operation(summary = "사전신청 API", description = "사전신청 데이터를 저장합니다.")
    public ResponseEntity<BaseResponse<Object>> register(
        @RequestBody CreatevalueRegisterRequestVo createvalueRegisterRequestVo
    ) {
        return ResponseEntity.ok(createvalueService.register(createvalueRegisterRequestVo));
    }
}
