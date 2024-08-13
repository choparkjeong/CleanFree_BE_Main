package site.cleanfree.be_main.visa.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.visa.domain.Visa;
import site.cleanfree.be_main.visa.domain.VisaAccess;
import site.cleanfree.be_main.visa.infrastructure.VisaAccessRepository;
import site.cleanfree.be_main.visa.infrastructure.VisaRepository;
import site.cleanfree.be_main.visa.vo.VisaRegisterRequestVo;

@Service
@RequiredArgsConstructor
public class VisaService {

    private final VisaRepository visaRepository;
    private final VisaAccessRepository visaAccessRepository;

    public BaseResponse<Object> register(VisaRegisterRequestVo visaRegisterRequestVo) {
        try {
            visaRepository.save(Visa.builder()
                .name(visaRegisterRequestVo.name())
                .phoneNumber(visaRegisterRequestVo.phoneNumber())
                .build());

            return BaseResponse.builder()
                .success(true)
                .errorCode(null)
                .message("success to save registration")
                .data(null)
                .build();
        } catch (Exception e) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("fail to save registration")
                .data(null)
                .build();
        }
    }

    public BaseResponse<Object> access(String clientIp) {
        Optional<VisaAccess> visaAccessOpt = visaAccessRepository.findVisaAccessByIp(clientIp);

        if (visaAccessOpt.isPresent()) {
            VisaAccess visaAccess = visaAccessOpt.get();
            visaAccessRepository.save(VisaAccess.builder()
                .id(visaAccess.getId())
                .ip(visaAccess.getIp())
                .count(visaAccess.getCount() + 1)
                .build());
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. update count.")
                .build();
        }

        visaAccessRepository.save(VisaAccess.builder()
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
