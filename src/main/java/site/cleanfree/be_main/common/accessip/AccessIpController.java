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
import site.cleanfree.be_main.common.ClientIpAccessor;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.consultant.application.ConsultantService;
import site.cleanfree.be_main.cookingstation.CookingStationService;
import site.cleanfree.be_main.cozyhouse.application.CozyHouseService;
import site.cleanfree.be_main.cozyquick.application.CozyquickService;
import site.cleanfree.be_main.createvalue.application.CreatevalueService;
import site.cleanfree.be_main.curesilver.CureSilverService;
import site.cleanfree.be_main.visa.application.VisaService;

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
    private final CozyHouseService cozyHouseService;

    @PostMapping
    public ResponseEntity<BaseResponse<Object>> access(
        HttpServletRequest request,
        @RequestBody IpSaveRequestVo ipSaveRequestVo
    ) {
        String clientIp = ClientIpAccessor.getIp(request);

        String service = ipSaveRequestVo.getService();
        return switch (service) {
            case "carrycabin" -> ResponseEntity.ok(carryCabinService.access(clientIp));
            case "consultant" -> ResponseEntity.ok(consultantService.access(clientIp));
            case "cookingstation" ->
                ResponseEntity.ok(cookingStationService.access(clientIp));
            case "cozyquick" -> ResponseEntity.ok(cozyquickService.access(clientIp));
            case "createvalue" -> ResponseEntity.ok(createvalueService.access(clientIp));
            case "curesilver" -> ResponseEntity.ok(cureSilverService.access(clientIp));
            case "clearvisa" -> ResponseEntity.ok(visaService.access(clientIp));
            case "cozyhouse" -> ResponseEntity.ok(cozyHouseService.access(clientIp));
            default -> ResponseEntity.ok(BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.BAD_DATA.getCode())
                .message("not existed service")
                .data(null)
                .build());
        };
    }
}
