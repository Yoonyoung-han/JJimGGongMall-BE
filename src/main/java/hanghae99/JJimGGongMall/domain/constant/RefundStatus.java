package hanghae99.JJimGGongMall.domain.constant;

public enum RefundStatus {
    REQUESTED("환불 요청됨"),
    PROCESSING("환불 처리 중"),
    COMPLETED("환불 완료"),
    FAILED("환불 실패");

    private final String description;

    RefundStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


