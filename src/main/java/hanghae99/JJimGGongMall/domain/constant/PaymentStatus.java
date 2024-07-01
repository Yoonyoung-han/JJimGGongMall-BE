package hanghae99.JJimGGongMall.domain.constant;

public enum PaymentStatus {
    INITIALIZED("결제 초기화"),
    PAID("결제 완료"),
    FAILED("결제 실패"),

    PARTIAL_CANCELLED("부분 결제 취소"),
    CANCELLED("결제 취소");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
