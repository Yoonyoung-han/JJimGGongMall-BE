package hanghae99.JJimGGongMall.domain;

import hanghae99.JJimGGongMall.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Entity
@Table(name = "refunds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Refund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private Long id;

    private Long orderId;
    private BigDecimal amount;
    private String refundStatus;
    private String refundType;
    private String refundInfo;

    @Builder
    private Refund(Long orderId, BigDecimal amount, String refundStatus, String refundType, String refundInfo) {
        this.orderId = orderId;
        this.amount = amount;
        this.refundStatus = refundStatus;
        this.refundType = refundType;
        this.refundInfo = refundInfo;
    }
}
