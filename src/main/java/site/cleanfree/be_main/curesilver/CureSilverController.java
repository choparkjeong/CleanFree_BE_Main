package site.cleanfree.be_main.curesilver;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;

@RestController
@RequestMapping("/api/v1/curesilver")
@RequiredArgsConstructor
@Tag(name = "curesilver API", description = "요양원 노인 관리 솔루션 API")
public class CureSilverController {

    private final CureSilverService cureSilverService;

    public ResponseEntity<BaseResponse<Object>> register(
        @RequestBody CureSilverRegisterRequestVo cureSilverRegisterRequestVo
    ) {
        return ResponseEntity.ok(cureSilverService.register(cureSilverRegisterRequestVo));
    }
}
