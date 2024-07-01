package hanghae99.JJimGGongMall.domain.constant;

public enum DeliveryStatus {

    NOT_READY("상품 확인 중"),
    READY("배송 준비 중"),
    IN_TRANSIT("배송 중"),
    DELIVERED("배송 완료"),
    FAILED("배송 실패");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

