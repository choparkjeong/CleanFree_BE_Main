package site.cleanfree.be_main.createeasy.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.ClientIpAccessor;
import site.cleanfree.be_main.createeasy.vo.CreateEasyRegisterRequestVo;
import site.cleanfree.be_main.createeasy.application.CreateEasyService;

@RestController
@RequestMapping("/api/v1/createvalue")
@RequiredArgsConstructor
@Tag(name = "create easy API", description = "인플루언서 컨텐츠 기획 API")
public class CreateEasyController {

    private final CreateEasyService createEasyService;

    @PostMapping("/register")
    @Operation(summary = "사전 신청 API", description = "사전 신청 데이터를 저장합니다.")
    public ResponseEntity<BaseResponse<Object>> search(
        @RequestBody CreateEasyRegisterRequestVo createEasyRegisterRequestVo
    ) {
        return ResponseEntity.ok(createEasyService.register(createEasyRegisterRequestVo));
    }
}
