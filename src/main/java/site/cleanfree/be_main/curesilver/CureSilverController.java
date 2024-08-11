package site.cleanfree.be_main.curesilver;

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

@RestController
@RequestMapping("/api/v1/curesilver")
@RequiredArgsConstructor
@Tag(name = "curesilver API", description = "요양원 노인 관리 솔루션 API")
public class CureSilverController {

    private final CureSilverService cureSilverService;

    @PostMapping("/register")
    @Operation(summary = "사전 신청 API", description = "ip 주소를 기준으로 사전 신청을 저장합니다.")
    public ResponseEntity<BaseResponse<Object>> register(
        HttpServletRequest request,
        @RequestBody CureSilverRegisterRequestVo cureSilverRegisterRequestVo
    ) {
        String clientIp = ClientIpAccessor.getIp(request);
        return ResponseEntity.ok(cureSilverService.register(clientIp));
    }
}
