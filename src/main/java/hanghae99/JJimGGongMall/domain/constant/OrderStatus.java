package hanghae99.JJimGGongMall.domain.constant;

public enum OrderStatus {
    CREATED("주문 생성됨"),
    PROCESSING("처리 중"),
    SHIPPED("배송 중"),
    DELIVERED("배송 완료"),
    PARTIAL_CANCELLED("주문 부분 취소됨"),
    CANCELLED("주문 취소됨"),
    REFUNDED("환불 완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
