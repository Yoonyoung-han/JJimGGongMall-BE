package hanghae99.JJimGGongMall.service;

import hanghae99.JJimGGongMall.common.security.repository.RefreshTokenRepository;
import hanghae99.JJimGGongMall.common.util.JwtUtil;
import hanghae99.JJimGGongMall.common.util.RedisUtil;
import hanghae99.JJimGGongMall.domain.User;
import hanghae99.JJimGGongMall.dto.MailDto;
import hanghae99.JJimGGongMall.dto.request.RequestSignInDto;
import hanghae99.JJimGGongMall.dto.request.RequestSignUpDto;
import hanghae99.JJimGGongMall.repository.interfaces.AddressRepository;
import hanghae99.JJimGGongMall.repository.interfaces.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AddressRepository addressRepository;

    // 이메일 중복 확인 -> userService
    // 핸드폰 중복 확인 -> userService

    public void sendEmail(MailDto mailDto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(mailDto.getEmail());
        helper.setSubject("찜꽁몰에서 메일 인증 코드를 확인하세요.");

        Context context = new Context();
        String authCode = createdCode();
        context.setVariable("code", authCode);

        String html = templateEngine.process("emailTemplate", context);
        helper.setText(html, true);

        redisUtil.setDataExpire(mailDto.getEmail(), authCode, 60 * 10L);

        mailSender.send(message);
    }

    //난수를 만드는 메소드 0~9와 a~z까지의 숫자와 문자를 섞어서 6자리 난수를 만든다.
    private String createdCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <=57 || i >=65) && (i <= 90 || i>= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Transactional
    public void verifyMail(MailDto mailDto) {
        String authNum = redisUtil.getData(mailDto.getEmail());

        if (authNum == null || !authNum.equals(mailDto.getCode())) {
            throw new IllegalArgumentException("Invalid verification code");
        }

        // 인증 성공 로직
        log.info("Email verification successful for {}", mailDto.getEmail());

        // Redis에서 데이터 삭제 (인증 완료 후 데이터는 필요 없으므로 삭제)
        redisUtil.deleteData(mailDto.getEmail());
    }

    @Transactional
    public User signUp(RequestSignUpDto request) {
        request.encodingPassword(passwordEncoder);
        User user = User.of(request);
        return userRepository.save(user);
    }

    @Transactional
    public Map<String, String> signIn(RequestSignInDto request) {
        String accountName = request.getAccountName();
        String password = request.getPassword();
        User user = userRepository.findByAccountName(accountName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        String role = user.getRole().name();

        String accessToken = jwtUtil.generateAccessToken(accountName,role);
        String refreshToken = jwtUtil.generateRefreshToken(accountName,role, user.getId());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    public Map<String, String> checkRefreshToken(String refreshToken){
        if (jwtUtil.validateToken(refreshToken)){
            Claims userInfo = jwtUtil.getUserInfoFromToken(refreshToken);
            String accountName = userInfo.get("username",String.class);
            String newAccessToken = jwtUtil.generateAccessToken(accountName,userInfo.get("role",String.class));

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            tokens.put("refreshToken", refreshToken);

            return tokens;
        } else {
          throw new RuntimeException("Invalid refresh token");
        }
    }

    public void deleteToken(Long userId) {
        // 예시: Redis를 사용한 토큰 삭제 로직
        refreshTokenRepository.deleteById(userId);
    }
}
