package site.cleanfree.be_main.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.auth.application.AuthService;
import site.cleanfree.be_main.auth.dto.MemberSnsLoginRequestDto;
import site.cleanfree.be_main.auth.dto.TokenResponseDto;
import site.cleanfree.be_main.auth.vo.MemberSignupRequestVo;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 관리 API")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인에 성공하면 토큰을 반환합니다. 회원이 아닌 경우 HTTP METHOD 201에 토큰을 빈 문자열로 반환합니다.")
    public ResponseEntity<BaseResponse<Object>> login(
        @RequestBody MemberSnsLoginRequestDto memberSnsLoginRequestDto) {
        TokenResponseDto tokenResponseDto = authService.snsLogin(memberSnsLoginRequestDto);
        String token = tokenResponseDto.getAccessToken();

        if (token == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .header(HttpHeaders.AUTHORIZATION, "")
                .body(BaseResponse.builder()
                    .success(true)
                    .errorCode(ErrorStatus.NOT_EXISTED_MEMBER.getCode())
                    .message("not existed member")
                    .data(null)
                    .build());
        }
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, token)
            .body(BaseResponse.successResponse("login success.", null));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "회원가입에 성공하면 Authorization 헤더에 토큰을 반환하지만 실패하면 빈 문자열을 반환합니다.")
    public ResponseEntity<BaseResponse<Object>> signup(
        @RequestBody MemberSignupRequestVo memberSignupRequestVo
    ) {
        TokenResponseDto tokenResponseDto = authService.signup(memberSignupRequestVo);
        String token = tokenResponseDto.getAccessToken();

        if (token == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .header(HttpHeaders.AUTHORIZATION, "")
                .body(BaseResponse.builder()
                    .success(false)
                    .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                    .message("signup data save fail")
                    .data(null)
                    .build());
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, token)
            .body(BaseResponse.successResponse("signup success.", null));
    }
}
