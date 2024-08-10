package site.cleanfree.be_main.createvalue.application;

import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.createvalue.vo.CreatevalueSearchRequestVo;

public interface CreatevalueService {

    BaseResponse<Object> search(CreatevalueSearchRequestVo createvalueSearchRequestVo);

}
