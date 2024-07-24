package site.cleanfree.be_main.auth.application;

import lombok.RequiredArgsConstructor;
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
import site.cleanfree.be_main.security.JwtTokenProvider;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final SnsInfoRepository snsInfoRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public TokenResponseDto snsLogin(MemberSnsLoginRequestDto memberSnsLoginRequestDto) {
        Optional<SnsInfo> optionalSnsInfo = snsInfoRepository.findBySnsId(memberSnsLoginRequestDto.getSnsId());

        if (optionalSnsInfo.isEmpty()) {
            String uuid = generateUuid();
            memberSnsLoginRequestDto.setUuid(uuid);

            // SnsInfo 및 Member 정보 저장
            Member member = Member.converter(memberSnsLoginRequestDto);
            memberRepository.save(member);
            snsInfoRepository.save(SnsInfo.converter(memberSnsLoginRequestDto, member));

            // token 생성
            String token = createToken(uuid);
            return TokenResponseDto.builder()
                    .accessToken(token)
                    .uuid(uuid)
                    .build();
        }

        // SNS 정보가 존재하는 경우 처리
        SnsInfo snsInfo = optionalSnsInfo.get();
        String token = createToken(snsInfo.getMember().getUuid());
        return TokenResponseDto.builder()
                .accessToken(token)
                .uuid(snsInfo.getMember().getUuid())
                .build();
    }

    // token 생성
    private String createToken(String uuid) {
        UserDetails userDetails = User.withUsername(uuid).password(uuid)
                .roles("USER").build();
        return jwtTokenProvider.generateToken(userDetails);
    }

    // uuid 생성(숫자 5개, 알파벳 소문자 5개 조합)
    public String generateUuid() {
        final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }
        for (int i = 0; i < 5; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}