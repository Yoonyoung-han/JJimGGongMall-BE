package hanghae99.JJimGGongMall.domain.constant;

public enum PgPaymentStatus {
    REQUESTED("PG 결제 요청됨"),
    AUTHORIZED("PG 결제 승인됨"),
    CANCELLED("PG 결제 취소됨"),
    REFUNDED("PG 결제 환불됨");

    private final String description;

    PgPaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
