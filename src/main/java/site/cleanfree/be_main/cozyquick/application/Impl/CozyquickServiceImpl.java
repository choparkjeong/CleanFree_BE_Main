package site.cleanfree.be_main.cozyquick.application.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.UuidProvider;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.cozyquick.application.CozyquickService;
import site.cleanfree.be_main.cozyquick.domain.Cozyquick;
import site.cleanfree.be_main.cozyquick.infrastructure.CozyquickRepository;
import site.cleanfree.be_main.cozyquick.vo.CozyquickSearchRequestVo;

@Service
@RequiredArgsConstructor
public class CozyquickServiceImpl implements CozyquickService {

    private final CozyquickRepository cozyquickRepository;

    @Override
    public BaseResponse<Object> search(CozyquickSearchRequestVo cozyquickSearchRequestVo) {
        String searchId = UuidProvider.generateAnyId();

        try {
            cozyquickRepository.save(Cozyquick.builder()
                .searchId(searchId)
                .search(cozyquickSearchRequestVo.getSearch())
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
}
