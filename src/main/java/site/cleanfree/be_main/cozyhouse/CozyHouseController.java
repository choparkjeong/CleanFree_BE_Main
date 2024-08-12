package site.cleanfree.be_main.cozyhouse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.ClientIpAccessor;
import site.cleanfree.be_main.visa.VisaRegisterRequestVo;

@Slf4j
@RestController
@RequestMapping("/api/v1/cozyhouse")
@RequiredArgsConstructor
public class CozyHouseController {

    private final CozyHouseService cozyHouseService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Object>> register(
        HttpServletRequest request,
        @RequestBody VisaRegisterRequestVo visaRegisterRequestVo
    ) {
        String clientIp = ClientIpAccessor.getIp(request);
        return ResponseEntity.ok(cozyHouseService.register(clientIp));
    }
}
