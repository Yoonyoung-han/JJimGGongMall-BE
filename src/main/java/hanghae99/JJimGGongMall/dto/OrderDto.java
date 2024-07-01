package hanghae99.JJimGGongMall.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private String orderNumber;
    private BigDecimal totalAmount;
    private String orderStatus;
    private List<OrderItemDto> orderItemList;

    @Builder
    public OrderDto(String orderNumber, BigDecimal totalAmount, String orderStatus, List<OrderItemDto> orderItemList) {
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderItemList = orderItemList;
    }
}
