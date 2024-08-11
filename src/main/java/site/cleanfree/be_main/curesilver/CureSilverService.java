package site.cleanfree.be_main.curesilver;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.accessip.IpSaveRequestVo;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.cookingstation.CookingStation;
import site.cleanfree.be_main.createvalue.domain.CreatevalueAccess;

@Service
@RequiredArgsConstructor
public class CureSilverService {

    private final CureSilverRepository cureSilverRepository;
    private final CureSilverAccessRepository cureSilverAccessRepository;

    public BaseResponse<Object> register(CureSilverRegisterRequestVo cureSilverRegisterRequestVo) {
        String ip = cureSilverRegisterRequestVo.getIp();
        Optional<CureSilver> cureSilverOpt = cureSilverRepository.findCureSilverByIp(ip);

        if (cureSilverOpt.isPresent()) {
            return BaseResponse.<Object>builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already applied. not registered.")
                .build();
        }

        try {
            cureSilverRepository.save(CureSilver.builder()
                .ip(cureSilverRegisterRequestVo.getIp())
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
        Optional<CureSilverAccess> cureSilverAccessOpt = cureSilverAccessRepository.findCureSilverAccessByIp(
            ipSaveRequestVo.getIp());

        if (cureSilverAccessOpt.isPresent()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. fail to save ip.")
                .data(null)
                .build();
        }

        cureSilverAccessRepository.save(CureSilverAccess.builder()
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
