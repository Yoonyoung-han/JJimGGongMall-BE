package hanghae99.JJimGGongMall.controller;

import hanghae99.JJimGGongMall.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> sendVerificationCode() {
        log.debug("Sending verification code...");
        return ApiResponse.ok("test success");
    }
}
