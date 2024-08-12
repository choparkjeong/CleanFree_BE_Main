package site.cleanfree.be_main.consultant.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.consultant.domain.Consultant;
import site.cleanfree.be_main.consultant.domain.ConsultantAccess;
import site.cleanfree.be_main.consultant.infrastructure.ConsultantAccessRepository;
import site.cleanfree.be_main.consultant.infrastructure.ConsultantRepository;
import site.cleanfree.be_main.consultant.vo.ConsultantRegisterRequestVo;

@Service
@RequiredArgsConstructor
public class ConsultantService {

    private final ConsultantRepository consultantRepository;
    private final ConsultantAccessRepository consultantAccessRepository;

    public BaseResponse<Object> register(String clientIp,
        ConsultantRegisterRequestVo consultantRegisterRequestVo) {
        Optional<Consultant> consultantOpt = consultantRepository.findConsultantByIp(clientIp);

        if (consultantOpt.isPresent()) {
            return BaseResponse.<Object>builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already applied. not registered.")
                .build();
        }

        try {
            consultantRepository.save(Consultant.builder()
                .ip(clientIp)
                .name(consultantRegisterRequestVo.name())
                .contact(consultantRegisterRequestVo.contact())
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
        Optional<ConsultantAccess> consultantAccessOpt = consultantAccessRepository.findConsultantAccessByIp(
            clientIp);

        if (consultantAccessOpt.isPresent()) {
            ConsultantAccess consultantAccess = consultantAccessOpt.get();
            consultantAccessRepository.save(ConsultantAccess.builder()
                .id(consultantAccess.getId())
                .ip(consultantAccess.getIp())
                .count(consultantAccess.getCount() + 1)
                .build());
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. update count.")
                .data(null)
                .build();
        }

        consultantAccessRepository.save(ConsultantAccess.builder()
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
