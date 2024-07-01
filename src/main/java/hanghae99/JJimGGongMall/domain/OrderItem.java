package hanghae99.JJimGGongMall.domain;

import hanghae99.JJimGGongMall.common.BaseEntity;
import hanghae99.JJimGGongMall.domain.constant.DeliveryStatus;
import hanghae99.JJimGGongMall.domain.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combination_id")
    private ProductOptionCombination optionCombination;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    private Long quantity;
    private BigDecimal price;
    private boolean refundCheck;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    public OrderItem(Order order, Product product, ProductOptionCombination optionCombination, DeliveryStatus deliveryStatus,
                     Address shippingAddress, Long quantity, BigDecimal price, boolean refundCheck, OrderStatus orderStatus) {
        this.order = order;
        this.product = product;
        this.optionCombination = optionCombination;
        this.deliveryStatus = deliveryStatus;
        this.shippingAddress = shippingAddress;
        this.quantity = quantity;
        this.price = price;
        this.refundCheck = refundCheck;
        this.orderStatus = orderStatus;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setRefundCheck(boolean refundCheck) {
        this.refundCheck = refundCheck;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
