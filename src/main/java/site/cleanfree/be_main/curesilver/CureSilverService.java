package site.cleanfree.be_main.curesilver;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.cookingstation.CookingStation;

@Service
@RequiredArgsConstructor
public class CureSilverService {

    private final CureSilverRepository cureSilverRepository;

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
}
