package hanghae99.JJimGGongMall.controller;

import hanghae99.JJimGGongMall.common.ApiResponse;
import hanghae99.JJimGGongMall.common.util.JwtUtil;
import hanghae99.JJimGGongMall.domain.User;
import hanghae99.JJimGGongMall.dto.request.MailDto;
import hanghae99.JJimGGongMall.dto.request.RequestSignInDto;
import hanghae99.JJimGGongMall.dto.request.RequestSignUpDto;
import hanghae99.JJimGGongMall.dto.response.ResponseSignUpDto;
import hanghae99.JJimGGongMall.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    @PostMapping("/verification-code/request")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> sendVerificationCode(@RequestBody MailDto request) {
        try {
            authService.sendEmail(request);
            return ApiResponse.ok("Email sent successfully");
        } catch (MessagingException e) {
            return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to send verification code. Please try again later. \n " + e.getMessage());
        }
    }

    @PostMapping("/verification-code/verify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<String> checkVerification(@RequestBody MailDto request) {
        try {
            authService.verifyMail(request);
            return ApiResponse.ok("Email verified successfully.");
        } catch (IllegalArgumentException e) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<String> signUp(@RequestBody RequestSignUpDto request) {
        try{
            User savedUser = authService.signUp(request);
            return ApiResponse.ok(String.valueOf(ResponseSignUpDto.of(savedUser)));
        } catch (Exception e){
            return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Map<String, String>> signIn(@RequestBody RequestSignInDto request, HttpServletResponse response) {
        Map<String, String> tokens = authService.signIn(request);

        // 액세스 토큰 쿠키 설정
        Cookie accessTokenCookie = new Cookie("accessToken", Base64.getUrlEncoder().encodeToString(tokens.get("accessToken").getBytes()));
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // HTTPS에서만 전송되도록 설정
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60); // 1시간 유효

        // 응답에 쿠키 추가
        response.addCookie(accessTokenCookie);

        // 응답 헤더에 액세스 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtUtil.AUTHORIZATION_HEADER, tokens.get("accessToken"));

        return ResponseEntity.ok().headers(headers).body(tokens);
    }


}
