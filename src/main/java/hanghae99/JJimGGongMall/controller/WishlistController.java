package hanghae99.JJimGGongMall.controller;

import hanghae99.JJimGGongMall.common.ApiResponse;
import hanghae99.JJimGGongMall.common.security.annotation.AuthUserId;
import hanghae99.JJimGGongMall.dto.ProductDto;
import hanghae99.JJimGGongMall.dto.request.RequestWishlistDto;
import hanghae99.JJimGGongMall.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ApiResponse<String> addWishlist(@AuthUserId Long userId, @RequestBody RequestWishlistDto request){
        String result = wishlistService.addWishlist(userId, request);
        return ApiResponse.of(HttpStatus.CREATED,result);
    }

    @GetMapping
    public ApiResponse<List<ProductDto>> getWishlist(@AuthUserId Long userId){
        List<ProductDto> result = wishlistService.getWishlist(userId);
        return ApiResponse.ok(result);
    }

    @DeleteMapping
    public ApiResponse<String> removeWishlist(@AuthUserId Long userId, @RequestBody RequestWishlistDto request){
        String result = wishlistService.removeWishlist(userId,request);
        return ApiResponse.ok(result);
    }
}
