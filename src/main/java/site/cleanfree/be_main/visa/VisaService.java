package site.cleanfree.be_main.visa;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.accessip.IpSaveRequestVo;
import site.cleanfree.be_main.common.exception.ErrorStatus;

@Service
@RequiredArgsConstructor
public class VisaService {

    private final VisaRepository visaRepository;
    private final VisaAccessRepository visaAccessRepository;

    public BaseResponse<Object> register(VisaRegisterRequestVo visaRegisterRequestVo) {
        String ip = visaRegisterRequestVo.getIp();
        Optional<Visa> visaOpt = visaRepository.findVisaByIp(ip);

        if (visaOpt.isPresent()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already registered")
                .build();
        }

        visaRepository.save(Visa.builder()
            .ip(ip)
            .build());

        return BaseResponse.builder()
            .success(true)
            .errorCode(null)
            .message("success to save ip.")
            .data(null)
            .build();
    }

    public BaseResponse<Object> access(IpSaveRequestVo ipSaveRequestVo) {
        String ip = ipSaveRequestVo.getIp();
        Optional<VisaAccess> visaAccessOpt = visaAccessRepository.findVisaAccessByIp(ip);

        if (visaAccessOpt.isPresent()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already accessed")
                .build();
        }

        visaAccessRepository.save(VisaAccess.builder()
            .ip(ip)
            .build());

        return BaseResponse.builder()
            .success(true)
            .errorCode(null)
            .message("success to save ip.")
            .data(null)
            .build();
    }
}
