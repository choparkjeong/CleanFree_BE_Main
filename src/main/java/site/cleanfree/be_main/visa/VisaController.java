package site.cleanfree.be_main.visa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;

@RestController
@RequestMapping("/api/v1/visa")
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
