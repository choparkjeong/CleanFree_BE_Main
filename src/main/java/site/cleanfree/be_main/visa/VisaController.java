package site.cleanfree.be_main.visa;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/clearvisa")
@RequiredArgsConstructor
public class VisaController {

    private final VisaService visaService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Object>> register(
        HttpServletRequest request,
        @RequestBody VisaRegisterRequestVo visaRegisterRequestVo
    ) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        log.info("clientIp: {}", clientIp);
        return ResponseEntity.ok(visaService.register(visaRegisterRequestVo));
    }
}
