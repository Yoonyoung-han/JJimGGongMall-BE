package hanghae99.JJimGGongMall.controller;

import hanghae99.JJimGGongMall.common.ApiResponse;
import hanghae99.JJimGGongMall.common.security.annotation.AuthUserId;
import hanghae99.JJimGGongMall.dto.OrderDto;
import hanghae99.JJimGGongMall.dto.request.RequestOrderDto;
import hanghae99.JJimGGongMall.dto.response.ResponseOrderDetailDto;
import hanghae99.JJimGGongMall.dto.response.ResponseOrderDto;
import hanghae99.JJimGGongMall.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<ResponseOrderDto> generateOrder(@AuthUserId Long userId, @RequestBody RequestOrderDto request){
        ResponseOrderDto result = orderService.createOrder(userId, request);
        return ApiResponse.of(HttpStatus.CREATED,result);
    }

    @GetMapping
    public ApiResponse<List<OrderDto>> getAllOrderList(@AuthUserId Long userId){
        List<OrderDto> result = orderService.getAllOrderList(userId);
        return ApiResponse.ok(result);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<ResponseOrderDetailDto> getOrderDetail(@AuthUserId Long userId, @PathVariable Long orderId){
        ResponseOrderDetailDto response = orderService.getOrderDetail(userId, orderId);
        return ApiResponse.ok(response);
    }

    @PutMapping("/cancel-all/{orderId}")
    public ApiResponse<String> cancelOrder(@PathVariable Long orderId){
        String response = orderService.cancelOrder(orderId);
        return ApiResponse.ok(response);
    }
}
