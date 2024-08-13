package site.cleanfree.be_main.carrycabin;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;

@Service
@RequiredArgsConstructor
public class CarryCabinService {

    private final CarryCabinRepository carryCabinRepository;
    private final CarryCabinAccessRepository carryCabinAccessRepository;

    public BaseResponse<Object> register(CarryCabinRegisterRequestVo carryCabinRegisterRequestVo) {
        try {
            carryCabinRepository.save(CarryCabin.builder()
                .name(carryCabinRegisterRequestVo.getName())
                .contact(carryCabinRegisterRequestVo.getContact())
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
        Optional<CarryCabinAccess> carryCabinAccessOpt = carryCabinAccessRepository.findCarryCabinAccessByIp(
            clientIp);

        if (carryCabinAccessOpt.isPresent()) {
            CarryCabinAccess carryCabinAccess = carryCabinAccessOpt.get();
            carryCabinAccessRepository.save(CarryCabinAccess.builder()
                .id(carryCabinAccess.getId())
                .ip(carryCabinAccess.getIp())
                .count(carryCabinAccess.getCount() + 1)
                .build());
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. update count.")
                .data(null)
                .build();
        }

        carryCabinAccessRepository.save(CarryCabinAccess.builder()
            .ip(clientIp)
            .count(1)
            .build());

        return BaseResponse.builder()
            .success(true)
            .errorCode(null)
            .message("success to save ip.")
            .data(null)
            .build();
    }
}
