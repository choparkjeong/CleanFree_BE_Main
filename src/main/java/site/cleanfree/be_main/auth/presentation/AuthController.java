package site.cleanfree.be_main.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.auth.application.AuthService;
import site.cleanfree.be_main.auth.dto.MemberSnsLoginRequestDto;
import site.cleanfree.be_main.auth.dto.TokenResponseDto;
import site.cleanfree.be_main.auth.infrastructure.SnsInfoRepository;
import site.cleanfree.be_main.common.BaseResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 관리 API")
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    public ResponseEntity<BaseResponse> login(
            @RequestBody MemberSnsLoginRequestDto memberSnsLoginRequestDto) {
        TokenResponseDto tokenResponseDto = authService.snsLogin(memberSnsLoginRequestDto);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, tokenResponseDto.getAccessToken())
                .header("uuid", tokenResponseDto.getUuid())
                .body(BaseResponse.successResponse());
    }
}
