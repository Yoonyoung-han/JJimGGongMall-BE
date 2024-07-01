package hanghae99.JJimGGongMall.domain;

import hanghae99.JJimGGongMall.common.exception.CustomLogicException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.JJimGGongMall.common.exception.ErrorCode.OUT_OF_STOCK;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_option_combination")
@Slf4j
public class ProductOptionCombination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combination_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productOptionCombination")
    List<CombinationDetail> combinationDetails;

    private String combinationValue;
    private int stock;
    private BigDecimal price;

    @Builder
    private ProductOptionCombination(String combinationValue, int stock, BigDecimal price) {
        this.combinationValue = combinationValue;
        this.stock = stock;
        this.price = price;
        this.combinationDetails = new ArrayList<>();
    }

    public void decrease(Long quantity) {
        verifyStockAvailable(quantity);
        stock -= quantity;
    }

    public void verifyStockAvailable(Long quantity) {
        if (stock - quantity < 0) {
            throw CustomLogicException.createBadRequestError(OUT_OF_STOCK);
        }
    }

    public void increase(Long quantity) {
        stock += quantity;
    }


}
