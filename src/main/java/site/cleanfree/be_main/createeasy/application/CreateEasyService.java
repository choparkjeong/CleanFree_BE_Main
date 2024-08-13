package site.cleanfree.be_main.createeasy.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.createeasy.domain.CreateEasy;
import site.cleanfree.be_main.createeasy.domain.CreateEasyAccess;
import site.cleanfree.be_main.createeasy.infrastructure.CreateEasyAccessRepository;
import site.cleanfree.be_main.createeasy.infrastructure.CreateEasyRepository;
import site.cleanfree.be_main.createeasy.vo.CreateEasyRegisterRequestVo;

@Service
@RequiredArgsConstructor
public class CreateEasyService {

    private final CreateEasyRepository createEasyRepository;
    private final CreateEasyAccessRepository createEasyAccessRepository;

    public BaseResponse<Object> register(CreateEasyRegisterRequestVo createEasyRegisterRequestVo) {
        try {
            createEasyRepository.save(CreateEasy.builder()
                .name(createEasyRegisterRequestVo.name())
                .phoneNumber(createEasyRegisterRequestVo.phoneNumber())
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
        Optional<CreateEasyAccess> createEasyAccessOpt = createEasyAccessRepository.findCreateEasyAccessByIp(
            clientIp);

        if (createEasyAccessOpt.isPresent()) {
            CreateEasyAccess createEasyAccess = createEasyAccessOpt.get();
            createEasyAccessRepository.save(CreateEasyAccess.builder()
                .id(createEasyAccess.getId())
                .ip(createEasyAccess.getIp())
                .count(createEasyAccess.getCount() + 1)
                .build());
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. update count.")
                .data(null)
                .build();
        }

        createEasyAccessRepository.save(CreateEasyAccess.builder()
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
