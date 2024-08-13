package site.cleanfree.be_main.carrycabin;

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
import site.cleanfree.be_main.cookingstation.CookingStationRegisterRequestVo;

@RestController
@RequestMapping("/api/v1/carrycabin")
@RequiredArgsConstructor
@Tag(name = "carrycabin API", description = "여행 가방 배달 서비스 API")
public class CarryCabinController {

    private final CarryCabinService carryCabinService;

    @PostMapping("/register")
    @Operation(summary = "사전 신청 API", description = "ip 주소를 기준으로 사전 신청을 저장합니다.")
    public ResponseEntity<BaseResponse<Object>> search(
        @RequestBody CarryCabinRegisterRequestVo carryCabinRegisterRequestVo
    ) {
        return ResponseEntity.ok(carryCabinService.register(carryCabinRegisterRequestVo));
    }

}
