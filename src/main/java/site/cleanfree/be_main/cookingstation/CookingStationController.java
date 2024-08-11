package site.cleanfree.be_main.cookingstation;

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
@RequestMapping("/api/v1/cookingstation")
@RequiredArgsConstructor
@Tag(name = "cookingstation API", description = "집밥 배선생 API")
public class CookingStationController {

    private final CookingStationService cookingStationService;

    @PostMapping("/register")
    @Operation(summary = "사전 신청 API", description = "ip 주소를 기준으로 사전 신청을 저장합니다.")
    public ResponseEntity<BaseResponse<Object>> search(
        HttpServletRequest request,
        @RequestBody CookingStationRegisterRequestVo cookingStationRegisterRequestVo
    ) {
        String clientIp = ClientIpAccessor.getIp(request);
        return ResponseEntity.ok(cookingStationService.register(clientIp));
    }

}
