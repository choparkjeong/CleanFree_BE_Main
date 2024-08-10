package site.cleanfree.be_main.createvalue.application.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.UuidProvider;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.createvalue.application.CreatevalueService;
import site.cleanfree.be_main.createvalue.domain.Createvalue;
import site.cleanfree.be_main.createvalue.infrastructure.CreatevalueRepository;
import site.cleanfree.be_main.createvalue.vo.CreatevalueSearchRequestVo;

@Service
@RequiredArgsConstructor
public class CreatevalueServiceImpl implements CreatevalueService {

    private final CreatevalueRepository createvalueRepository;

    public BaseResponse<Object> search(CreatevalueSearchRequestVo createvalueSearchRequestVo) {
        String searchId = UuidProvider.generateAnyId();

        try {
            createvalueRepository.save(Createvalue.builder()
                .searchId(searchId)
                .search(createvalueSearchRequestVo.getSearch())
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
