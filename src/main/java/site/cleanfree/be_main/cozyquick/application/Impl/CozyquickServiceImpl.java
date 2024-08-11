package site.cleanfree.be_main.cozyquick.application.Impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.accessip.IpSaveRequestVo;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.cozyquick.application.CozyquickService;
import site.cleanfree.be_main.cozyquick.domain.Cozyquick;
import site.cleanfree.be_main.cozyquick.domain.CozyquickAccess;
import site.cleanfree.be_main.cozyquick.infrastructure.CozyquickAccessRepository;
import site.cleanfree.be_main.cozyquick.infrastructure.CozyquickRepository;
import site.cleanfree.be_main.cozyquick.vo.CozyquickSearchRequestVo;

@Service
@RequiredArgsConstructor
public class CozyquickServiceImpl implements CozyquickService {

    private final CozyquickRepository cozyquickRepository;
    private final CozyquickAccessRepository cozyquickAccessRepository;

    @Override
    public BaseResponse<Object> search(CozyquickSearchRequestVo cozyquickSearchRequestVo, String clientIp) {
        Optional<Cozyquick> cozyquickOpt = cozyquickRepository.findCozyquickByIp(
            clientIp);

        if (cozyquickOpt.isPresent()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(null)
                .message("already existed ip. fail to save search data.")
                .data(null)
                .build();
        }

        try {
            cozyquickRepository.save(Cozyquick.builder()
                .search(cozyquickSearchRequestVo.getSearch())
                .ip(clientIp)
                .build());
            return BaseResponse.builder()
                .success(true)
                .errorCode(null)
                .message("success to save search data")
                .data(null)
                .build();
        } catch (Exception e) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("failed to save search data")
                .data(null)
                .build();
        }
    }

    @Override
    public BaseResponse<Object> access(String clientIp) {
        Optional<CozyquickAccess> cozyquickAccessOpt = cozyquickAccessRepository.findCozyquickAccessByIp(
            clientIp);

        if (cozyquickAccessOpt.isPresent()) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. fail to save ip.")
                .data(null)
                .build();
        }

        cozyquickAccessRepository.save(CozyquickAccess.builder()
            .ip(clientIp)
            .build());

        return BaseResponse.builder()
            .success(true)
            .errorCode(null)
            .message("success to save ip.")
            .data(null)
            .build();
    }
}
