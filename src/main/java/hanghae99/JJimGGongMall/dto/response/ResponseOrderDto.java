package hanghae99.JJimGGongMall.dto.response;

import hanghae99.JJimGGongMall.domain.OrderItem;
import hanghae99.JJimGGongMall.domain.constant.OrderStatus;
import hanghae99.JJimGGongMall.dto.OrderItemDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseOrderDto {

    private Long orderId; // 주문 ID
    private Long customerId; // 고객 ID
    private List<OrderItemDto> productList = new ArrayList<>(); // 상품 목록
    private String deliveryAddress; // 배송 주소
    private Long paymentId; // 결제 ID
    private String orderStatus; // 주문 상태
    private LocalDateTime orderDateTime; // 주문 일시


    @Builder
    public ResponseOrderDto(Long orderId, Long customerId, String deliveryAddress,
                            Long paymentId, String orderStatus, LocalDateTime orderDateTime) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.paymentId = paymentId;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
    }

    public void addProductList(List<OrderItem> orderItemList){
        this.productList.addAll(orderItemList.stream().map(OrderItemDto::toDto).toList());
    }
}
