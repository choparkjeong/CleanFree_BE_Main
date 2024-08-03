package site.cleanfree.be_main.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.cleanfree.be_main.auth.domain.Member;
import site.cleanfree.be_main.auth.domain.SnsInfo;
import site.cleanfree.be_main.auth.dto.MemberSnsLoginRequestDto;
import site.cleanfree.be_main.auth.dto.TokenResponseDto;
import site.cleanfree.be_main.auth.infrastructure.MemberRepository;
import site.cleanfree.be_main.auth.infrastructure.SnsInfoRepository;
import site.cleanfree.be_main.auth.vo.MemberSignupRequestVo;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.UuidProvider;
import site.cleanfree.be_main.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final SnsInfoRepository snsInfoRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private SnsInfo getSnsInfo(String snsId) {
        return snsInfoRepository.findBySnsId(snsId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponseDto snsLogin(MemberSnsLoginRequestDto memberSnsLoginRequestDto) {
        SnsInfo snsInfo = getSnsInfo(memberSnsLoginRequestDto.getSnsId());

        if (snsInfo == null) {
            log.info("First time login user.");
            return TokenResponseDto.builder()
                .accessToken(null)
                .build();
        }

        String uuid = snsInfo.getUuid();
        String token = createToken(uuid);
        return TokenResponseDto.builder()
            .accessToken(token)
            .build();
    }

    @Override
    @Transactional
    public TokenResponseDto signup(MemberSignupRequestVo memberSignupRequestVo) {
        SnsInfo snsInfo = getSnsInfo(memberSignupRequestVo.getSnsId());

        if (snsInfo == null) {
            String memberUuid = UuidProvider.generateMemberUuid();
            try {
                snsInfoRepository.save(SnsInfo.builder()
                    .snsId(memberSignupRequestVo.getSnsId())
                    .uuid(memberUuid)
                    .build());
                memberRepository.save(Member.converter(memberSignupRequestVo, memberUuid));
            } catch (Exception e) {
                log.info("error: {}", e.getMessage());
                return TokenResponseDto.builder()
                    .accessToken(null)
                    .build();
            }
            String token = createToken(memberUuid);
            return TokenResponseDto.builder()
                .accessToken(token)
                .build();
        }

        String token = createToken(snsInfo.getUuid());
        return TokenResponseDto.builder()
            .accessToken(token)
            .build();
    }

    private String createToken(String uuid) {
        UserDetails userDetails = User.withUsername(uuid).password(uuid)
            .roles("USER").build();
        return jwtTokenProvider.generateToken(userDetails);
    }

    @Override
    public BaseResponse<Boolean> isMember(MemberSnsLoginRequestDto memberSnsLoginRequestDto) {
        SnsInfo snsInfo = getSnsInfo(memberSnsLoginRequestDto.getSnsId());

        if (snsInfo == null) {
            return BaseResponse.<Boolean>builder()
                .success(true)
                .errorCode(null)
                .message("not exist member")
                .data(false)
                .build();
        }

        return BaseResponse.<Boolean>builder()
            .success(true)
            .errorCode(null)
            .message("exist member")
            .data(true)
            .build();
    }
}
