package site.cleanfree.be_main.auth.application;

import site.cleanfree.be_main.auth.dto.MemberSnsLoginRequestDto;
import site.cleanfree.be_main.auth.dto.TokenResponseDto;
import site.cleanfree.be_main.auth.vo.MemberSignupRequestVo;
import site.cleanfree.be_main.common.BaseResponse;

public interface AuthService {

    TokenResponseDto snsLogin(MemberSnsLoginRequestDto memberSnsLoginRequestDto);

    TokenResponseDto signup(MemberSignupRequestVo memberSignupRequestVo);

    BaseResponse<Boolean> isMember(MemberSnsLoginRequestDto memberSnsLoginRequestDto);
}
