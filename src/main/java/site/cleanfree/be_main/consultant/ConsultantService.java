package site.cleanfree.be_main.consultant;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.accessip.IpSaveRequestVo;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.curesilver.CureSilverAccess;

@Service
@RequiredArgsConstructor
public class ConsultantService {

    private final ConsultantRepository consultantRepository;
    private final ConsultantAccessRepository consultantAccessRepository;

    public BaseResponse<Object> register(ConsultantRegisterRequestVo consultantRegisterRequestVo) {
        String ip = consultantRegisterRequestVo.getIp();
        Optional<Consultant> consultantOpt = consultantRepository.findConsultantByIp(ip);

        if (consultantOpt.isPresent()) {
            return BaseResponse.<Object>builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already applied. not registered.")
                .build();
        }

        try {
            consultantRepository.save(Consultant.builder()
                .ip(consultantRegisterRequestVo.getIp())
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
        Optional<ConsultantAccess> consultantAccessOpt = consultantAccessRepository.findConsultantAccessByIp(
            ipSaveRequestVo.getIp());

        if (consultantAccessOpt.isPresent()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. fail to save ip.")
                .data(null)
                .build();
        }

        consultantAccessRepository.save(ConsultantAccess.builder()
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
