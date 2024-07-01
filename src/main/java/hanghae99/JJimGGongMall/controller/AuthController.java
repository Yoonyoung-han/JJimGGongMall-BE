package hanghae99.JJimGGongMall.controller;

import hanghae99.JJimGGongMall.common.ApiResponse;
import hanghae99.JJimGGongMall.common.security.UserDetailsImpl;
import hanghae99.JJimGGongMall.common.security.annotation.AuthUserId;
import hanghae99.JJimGGongMall.common.util.JwtUtil;
import hanghae99.JJimGGongMall.domain.User;
import hanghae99.JJimGGongMall.dto.MailDto;
import hanghae99.JJimGGongMall.dto.request.RequestSignInDto;
import hanghae99.JJimGGongMall.dto.request.RequestSignUpDto;
import hanghae99.JJimGGongMall.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;
    private final JwtUtil jwtUtil;

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
            return ApiResponse.ok("Sign up Success!!");
        } catch (Exception e){
            return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Map<String, String>> signIn(@RequestBody RequestSignInDto request, HttpServletResponse response) {
        Map<String, String> tokens = authService.signIn(request);

        // 응답에 쿠키 추가
        response.addCookie(setAccessTokenCookie(tokens));

        // 응답 헤더에 액세스 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtUtil.AUTHORIZATION_HEADER, tokens.get("accessToken"));

        return ApiResponse.of(HttpStatus.CREATED,headers,tokens);
    }

    @DeleteMapping("/sign-out")
    public ApiResponse<String> signOut(@AuthUserId Long userId) {
        authService.deleteToken(userId);
        return ApiResponse.ok("Sign out everywhere!");
    }

    @PutMapping("/re-issue")
    public ApiResponse<Map<String, String>> reIssueToken(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        String refreshToken = requestBody.get("refreshToken");
        Map<String, String> tokens = authService.checkRefreshToken(refreshToken);

        // 응답에 쿠키 추가
        response.addCookie(setAccessTokenCookie(tokens));

        // 응답 헤더에 액세스 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtUtil.AUTHORIZATION_HEADER, tokens.get("accessToken"));

        return ApiResponse.of(HttpStatus.CREATED,headers,tokens);
    }


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/refresh-token")
    public ApiResponse<String> refreshToken(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        String accountName = userDetails.getUsername();
        String role = userDetails.getUser().getRole().name();

        String newRefreshToken = jwtUtil.generateRefreshToken(accountName, role, userId);
        return ApiResponse.ok(newRefreshToken);
    }


    public Cookie setAccessTokenCookie(Map<String, String> tokens){
        // 액세스 토큰 쿠키 설정
        Cookie accessTokenCookie = new Cookie("accessToken", Base64.getUrlEncoder().encodeToString(tokens.get("accessToken").getBytes()));

        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // HTTPS에서만 전송되도록 설정
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60); // 1시간 유효

        return accessTokenCookie;
    }
}
