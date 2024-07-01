package hanghae99.JJimGGongMall.dto;

import hanghae99.JJimGGongMall.domain.OrderItem;
import hanghae99.JJimGGongMall.dto.response.ResponseOrderDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
    private Long productId; // 상품 ID
    private Long quantity; // 수량
    private Long optionCombinationId; // 옵션 조합 id

    public static OrderItemDto toDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .optionCombinationId(orderItem.getOptionCombination().getId())
                .build();
    }
}