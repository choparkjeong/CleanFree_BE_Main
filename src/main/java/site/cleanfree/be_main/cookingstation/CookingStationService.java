package site.cleanfree.be_main.cookingstation;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;

@Service
@RequiredArgsConstructor
public class CookingStationService {

    private final CookingStationRepository cookingStationRepository;
    private final CookingStationAccessRepository cookingStationAccessRepository;

    public BaseResponse<Object> register(String clientIp) {
        Optional<CookingStation> cookingStationOpt = cookingStationRepository.findCookingStationByIp(
            clientIp);

        if (cookingStationOpt.isPresent()) {
            return BaseResponse.<Object>builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already applied. not registered.")
                .build();
        }

        try {
            cookingStationRepository.save(CookingStation.builder()
                .ip(clientIp)
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

    public BaseResponse<Object> access(String clientIp) {
        Optional<CookingStationAccess> cookingStationAccessOpt = cookingStationAccessRepository.findCookingStationAccessByIp(
            clientIp);

        if (cookingStationAccessOpt.isPresent()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. fail to save ip.")
                .data(null)
                .build();
        }

        cookingStationAccessRepository.save(CookingStationAccess.builder()
            .ip(clientIp)
            .build());

        return BaseResponse.builder()
            .success(true)
            .errorCode(null)
            .message("success to save ip.")
            .data(null)
            .build();
    }
}
