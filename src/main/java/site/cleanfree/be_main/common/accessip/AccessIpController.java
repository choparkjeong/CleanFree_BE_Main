package site.cleanfree.be_main.common.accessip;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.carrycabin.CarryCabinService;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.consultant.ConsultantService;
import site.cleanfree.be_main.cookingstation.CookingStationService;
import site.cleanfree.be_main.cozyquick.application.CozyquickService;
import site.cleanfree.be_main.createvalue.application.CreatevalueService;
import site.cleanfree.be_main.curesilver.CureSilverService;
import site.cleanfree.be_main.visa.VisaService;

@Slf4j
@RestController
@RequestMapping("/api/v1/access")
@RequiredArgsConstructor
public class AccessIpController {

    private final CarryCabinService carryCabinService;
    private final ConsultantService consultantService;
    private final CookingStationService cookingStationService;
    private final CozyquickService cozyquickService;
    private final CreatevalueService createvalueService;
    private final CureSilverService cureSilverService;
    private final VisaService visaService;

    @PostMapping
    public ResponseEntity<BaseResponse<Object>> access(
        HttpServletRequest request,
        @RequestBody IpSaveRequestVo ipSaveRequestVo
    ) {
        String clientIp = request.getHeader("X-Forwarded-For");
        log.info("clientIp: {}", clientIp);
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
            log.info("clientIp: {}", clientIp);
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
            log.info("clientIp: {}", clientIp);
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
            log.info("clientIp: {}", clientIp);
        }
        log.info("clientIp: {}", clientIp);

        String service = ipSaveRequestVo.getService();
        return switch (service) {
            case "carrycabin" -> ResponseEntity.ok(carryCabinService.access(ipSaveRequestVo));
            case "consultant" -> ResponseEntity.ok(consultantService.access(ipSaveRequestVo));
            case "cookingstation" ->
                ResponseEntity.ok(cookingStationService.access(ipSaveRequestVo));
            case "cozyquick" -> ResponseEntity.ok(cozyquickService.access(ipSaveRequestVo));
            case "createvalue" -> ResponseEntity.ok(createvalueService.access(ipSaveRequestVo));
            case "curesilver" -> ResponseEntity.ok(cureSilverService.access(ipSaveRequestVo));
            case "clearvisa" -> ResponseEntity.ok(visaService.access(ipSaveRequestVo));
            default -> ResponseEntity.ok(BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.BAD_DATA.getCode())
                .message("not existed service")
                .data(null)
                .build());
        };
    }
}
