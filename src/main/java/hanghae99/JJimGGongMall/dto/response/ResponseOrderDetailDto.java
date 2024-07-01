package hanghae99.JJimGGongMall.dto.response;

import hanghae99.JJimGGongMall.domain.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseOrderDetailDto {

    private Long paymentId;
    private String orderNumber;
    private String orderStatus;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private List<OrderItemDto> orderItems = new ArrayList<>();

    @Data
    @Builder
    public static class OrderItemDto {
        private Long productId;
        private String productName;
        private Long optionCombinationId;
        private String optionCombinationValue;
        private Long quantity;
        private BigDecimal price;
        private String deliveryStatus;
        private boolean isAvailRefund;

        public static OrderItemDto toDto(OrderItem orderItem) {
            return OrderItemDto.builder()
                    .productId(orderItem.getProduct().getId())
                    .deliveryStatus(orderItem.getDeliveryStatus().getDescription())
                    .isAvailRefund(orderItem.isRefundCheck())
                    .productName(orderItem.getProduct().getProductName())
                    .optionCombinationId(orderItem.getOptionCombination().getId())
                    .optionCombinationValue(orderItem.getOptionCombination().getCombinationValue())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();
        }
    }

    @Builder
    public ResponseOrderDetailDto(Long paymentId, String orderNumber, String orderStatus, LocalDateTime orderDate,
                                  BigDecimal totalAmount) {
        this.paymentId = paymentId;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public void addOrderItems(List<OrderItem> orderItemList){
        this.orderItems.addAll(orderItemList.stream().map(OrderItemDto::toDto).toList());
    }
}
