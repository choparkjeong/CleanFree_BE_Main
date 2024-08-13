package site.cleanfree.be_main.visa.presentation;

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
import site.cleanfree.be_main.visa.application.VisaService;
import site.cleanfree.be_main.visa.vo.VisaRegisterRequestVo;

@Slf4j
@RestController
@RequestMapping("/api/v1/clearvisa")
@RequiredArgsConstructor
public class VisaController {

    private final VisaService visaService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Object>> register(
        @RequestBody VisaRegisterRequestVo visaRegisterRequestVo
    ) {
        return ResponseEntity.ok(visaService.register(visaRegisterRequestVo));
    }
}
