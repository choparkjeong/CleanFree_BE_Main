package site.cleanfree.be_main.curesilver.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.curesilver.domain.CureSilver;
import site.cleanfree.be_main.curesilver.domain.CureSilverAccess;
import site.cleanfree.be_main.curesilver.infrastructure.CureSilverAccessRepository;
import site.cleanfree.be_main.curesilver.infrastructure.CureSilverRepository;
import site.cleanfree.be_main.curesilver.vo.CureSilverRegisterRequestVo;

@Service
@RequiredArgsConstructor
public class CureSilverService {

    private final CureSilverRepository cureSilverRepository;
    private final CureSilverAccessRepository cureSilverAccessRepository;

    public BaseResponse<Object> register(CureSilverRegisterRequestVo cureSilverRegisterRequestVo) {
        try {
            cureSilverRepository.save(CureSilver.builder()
                .name(cureSilverRegisterRequestVo.name())
                .phoneNumber(cureSilverRegisterRequestVo.phoneNumber())
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
        Optional<CureSilverAccess> cureSilverAccessOpt = cureSilverAccessRepository.findCureSilverAccessByIp(
            clientIp);

        if (cureSilverAccessOpt.isPresent()) {
            CureSilverAccess cureSilverAccess = cureSilverAccessOpt.get();
            cureSilverAccessRepository.save(CureSilverAccess.builder()
                .id(cureSilverAccess.getId())
                .ip(cureSilverAccess.getIp())
                .count(cureSilverAccess.getCount() + 1)
                .build());
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. fail to save ip.")
                .data(null)
                .build();
        }

        cureSilverAccessRepository.save(CureSilverAccess.builder()
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
