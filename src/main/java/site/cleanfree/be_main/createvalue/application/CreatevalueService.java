package site.cleanfree.be_main.createvalue.application;

import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.createvalue.vo.CreatevalueRegisterRequestVo;

public interface CreatevalueService {

    BaseResponse<Object> register(CreatevalueRegisterRequestVo createvalueRegisterRequestVo);

    BaseResponse<Object> access(String clientIp);
}
