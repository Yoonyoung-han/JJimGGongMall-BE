package hanghae99.JJimGGongMall.controller;

import hanghae99.JJimGGongMall.common.ApiResponse;
import hanghae99.JJimGGongMall.common.security.annotation.AuthUserId;
import hanghae99.JJimGGongMall.dto.request.RequestCheckDuplicateDto;
import hanghae99.JJimGGongMall.dto.request.RequestUpdateUserInfoDto;
import hanghae99.JJimGGongMall.dto.response.GetUserInfoDTO;
import hanghae99.JJimGGongMall.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<GetUserInfoDTO> getUserInfo(@AuthUserId Long userId){
        GetUserInfoDTO result = userService.getUserInfo(userId);
        return ApiResponse.ok(result);
    }

    @GetMapping("/check-validate")
    public ApiResponse<String> isDuplicate(@RequestBody RequestCheckDuplicateDto request){
        boolean result = userService.isDuplicate(request);
        return ApiResponse.ok(result? "이미 있는 값 입니다.":"사용할 수 있는 값입니다.");
    }

    @PutMapping
    public ApiResponse<GetUserInfoDTO> updateUserInfo(@AuthUserId Long userId,@RequestBody RequestUpdateUserInfoDto request){
        GetUserInfoDTO result = userService.updateUserInfo(userId,request);
        return ApiResponse.ok(result);
    }

}
