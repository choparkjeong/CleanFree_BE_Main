package site.cleanfree.be_main.cookingstation;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.carrycabin.CarryCabinAccess;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.accessip.IpSaveRequestVo;
import site.cleanfree.be_main.common.exception.ErrorStatus;

@Service
@RequiredArgsConstructor
public class CookingStationService {

    private final CookingStationRepository cookingStationRepository;
    private final CookingStationAccessRepository cookingStationAccessRepository;

    public BaseResponse<Object> register(
        CookingStationRegisterRequestVo cookingStationRegisterRequestVo) {
        String ip = cookingStationRegisterRequestVo.getIp();
        Optional<CookingStation> cookingStationOpt = cookingStationRepository.findCookingStationByIp(
            ip);

        if (cookingStationOpt.isPresent()) {
            return BaseResponse.<Object>builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already applied. not registered.")
                .build();
        }

        try {
            cookingStationRepository.save(CookingStation.builder()
                .ip(cookingStationRegisterRequestVo.getIp())
                .build());

            return BaseResponse.<Object>builder()
                .success(true)
                .errorCode(null)
                .message("success save registered.")
                .build();
        } catch (Exception e) {
            return BaseResponse.<Object>builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("failed save registered.")
                .build();
        }
    }

    public BaseResponse<Object> access(IpSaveRequestVo ipSaveRequestVo) {
        Optional<CookingStationAccess> cookingStationAccessOpt = cookingStationAccessRepository.findCookingStationAccessByIp(
            ipSaveRequestVo.getIp());

        if (cookingStationAccessOpt.isPresent()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. fail to save ip.")
                .data(null)
                .build();
        }

        cookingStationAccessRepository.save(CookingStationAccess.builder()
            .ip(ipSaveRequestVo.getIp())
            .build());

        return BaseResponse.builder()
            .success(true)
            .errorCode(null)
            .message("success to save ip.")
            .data(null)
            .build();
    }
}
