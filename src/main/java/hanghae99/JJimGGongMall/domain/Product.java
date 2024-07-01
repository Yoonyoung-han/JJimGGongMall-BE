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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
@Slf4j
public class Product  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "prod_name")
    private String productName;

    @Column(name = "prod_desc")
    private String productDescription;
    private Long stock;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
    private String thumbnailUrl;

    @Builder
    public Product(Category category, String productName, String productDescription, Long stock, BigDecimal price, String thumbnailUrl) {
        this.category = category;
        this.productName = productName;
        this.productDescription = productDescription;
        this.stock = stock;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
    }

}
