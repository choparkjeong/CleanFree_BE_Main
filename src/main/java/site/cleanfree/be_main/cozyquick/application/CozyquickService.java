package site.cleanfree.be_main.cozyquick.application;

import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.accessip.IpSaveRequestVo;
import site.cleanfree.be_main.cozyquick.vo.CozyquickSearchRequestVo;

public interface CozyquickService {

    BaseResponse<Object> search(CozyquickSearchRequestVo cozyquickSearchRequestVo);

    BaseResponse<Object> access(IpSaveRequestVo ipSaveRequestVo);
}
