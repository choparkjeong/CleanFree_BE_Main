package site.cleanfree.be_main.auth.application;

import site.cleanfree.be_main.auth.dto.MemberSnsLoginRequestDto;
import site.cleanfree.be_main.auth.dto.TokenResponseDto;
import site.cleanfree.be_main.auth.vo.MemberSignupRequestVo;

public interface AuthService {

    TokenResponseDto snsLogin(MemberSnsLoginRequestDto memberSnsLoginRequestDto);

    TokenResponseDto signup(MemberSignupRequestVo memberSignupRequestVo);
}
