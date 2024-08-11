package site.cleanfree.be_main.curesilver;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;

@Service
@RequiredArgsConstructor
public class CureSilverService {

    private final CureSilverRepository cureSilverRepository;
    private final CureSilverAccessRepository cureSilverAccessRepository;

    public BaseResponse<Object> register(String clientIp) {
        Optional<CureSilver> cureSilverOpt = cureSilverRepository.findCureSilverByIp(clientIp);

        if (cureSilverOpt.isPresent()) {
            return BaseResponse.<Object>builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already applied. not registered.")
                .build();
        }

        try {
            cureSilverRepository.save(CureSilver.builder()
                .ip(clientIp)
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
