package site.cleanfree.be_main.auth.application;

import site.cleanfree.be_main.auth.dto.MemberSnsLoginRequestDto;
import site.cleanfree.be_main.auth.dto.TokenResponseDto;

public interface AuthService {
    TokenResponseDto snsLogin(MemberSnsLoginRequestDto memberSnsLoginRequestDto);
}
