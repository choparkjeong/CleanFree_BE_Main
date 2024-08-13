package site.cleanfree.be_main.cozyhouse.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.cozyhouse.application.CozyHouseService;
import site.cleanfree.be_main.cozyhouse.vo.CozyHouseRegisterRequestVo;

@Slf4j
@RestController
@RequestMapping("/api/v1/cozyhouse")
@RequiredArgsConstructor
public class CozyHouseController {

    private final CozyHouseService cozyHouseService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Object>> register(
        @RequestBody CozyHouseRegisterRequestVo cozyHouseRegisterRequestVo
    ) {
        return ResponseEntity.ok(cozyHouseService.register(cozyHouseRegisterRequestVo));
    }
}
