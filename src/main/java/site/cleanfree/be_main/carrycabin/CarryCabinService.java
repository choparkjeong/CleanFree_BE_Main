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

    public BaseResponse<Object> register(CarryCabinRegisterRequestVo carryCabinRegisterRequestVo) {
        String ip = carryCabinRegisterRequestVo.getIp();
        Optional<CarryCabin> carryCabinOpt = carryCabinRepository.findCarryCabinByIp(
            ip);

        if (carryCabinOpt.isPresent()) {
            return BaseResponse.<Object>builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already applied. not registered.")
                .build();
        }

        try {
            carryCabinRepository.save(CarryCabin.builder()
                .ip(carryCabinRegisterRequestVo.getIp())
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
}
