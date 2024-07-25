package site.cleanfree.be_main.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.cleanfree.be_main.auth.application.AuthService;
import site.cleanfree.be_main.auth.dto.MemberSnsLoginRequestDto;
import site.cleanfree.be_main.auth.dto.TokenResponseDto;
import site.cleanfree.be_main.auth.infrastructure.SnsInfoRepository;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.security.JwtTokenProvider;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 관리 API")
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인 API")
    public ResponseEntity<BaseResponse> login(
            @RequestBody MemberSnsLoginRequestDto memberSnsLoginRequestDto) {
        TokenResponseDto tokenResponseDto = authService.snsLogin(memberSnsLoginRequestDto);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, tokenResponseDto.getAccessToken())
                .body(BaseResponse.successResponse());
    }

    @GetMapping("/test")
    @Operation(summary = "로그인 테스트 API", description = "로그인 테스트 API")
    public ResponseEntity<BaseResponse> test(
            @RequestHeader String token
    ) {
        log.info("token >>> {}", token);
        return ResponseEntity.ok()
                .body(BaseResponse.builder()
                        .data("uuid >>> " + jwtTokenProvider.getUuid(token))
                        .build());
    }
}
