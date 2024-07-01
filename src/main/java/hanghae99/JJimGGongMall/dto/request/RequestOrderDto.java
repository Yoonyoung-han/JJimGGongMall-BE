package hanghae99.JJimGGongMall.dto.request;

import hanghae99.JJimGGongMall.domain.constant.PaymentStatus;
import hanghae99.JJimGGongMall.dto.OrderItemDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RequestOrderDto {

    private Long userId; // 사용자 ID
    private List<OrderItemDto> orderItems; // 주문 상품 목록
    private Long deliveryAddressId; // 배송 주소 ID
    private PaymentInfo paymentInfo; // 결제 정보

    @Data
    public static class PaymentInfo {
        private String paymentMethod; // 결제 수단 (신용카드, 계좌이체 등)
        private String paymentDetails; // 결제 상세 정보 (카드 번호, 계좌 번호 등)
        private int installmentMonth; // 할부 개월
        private String cardIssuer; // 카드 발급사
        private BigDecimal amount; // 결제 금액
    }

}
