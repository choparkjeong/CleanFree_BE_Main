package site.cleanfree.be_main.cozyquick.presentation;

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
import site.cleanfree.be_main.cozyquick.application.CozyquickService;
import site.cleanfree.be_main.cozyquick.vo.CozyquickSearchRequestVo;

@RestController
@RequestMapping("/api/v1/cozyquick")
@RequiredArgsConstructor
@Tag(name = "cozyquick API", description = "해외 구매 대행 서비스 API")
public class CozyquickController {

    private final CozyquickService cozyquickService;

    @PostMapping("/search")
    @Operation(summary = "검색 API", description = "검색어를 저장합니다.")
    public ResponseEntity<BaseResponse<Object>> search(
        HttpServletRequest request,
        @RequestBody CozyquickSearchRequestVo cozyquickSearchRequestVo
    ) {
        String clientIp = ClientIpAccessor.getIp(request);
        return ResponseEntity.ok(cozyquickService.search(cozyquickSearchRequestVo, clientIp));
    }

}
