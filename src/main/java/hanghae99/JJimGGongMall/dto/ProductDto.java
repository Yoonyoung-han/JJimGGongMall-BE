package hanghae99.JJimGGongMall.dto;

import hanghae99.JJimGGongMall.domain.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long productId;
    private Long categoryId;
    private String productName;
    private String description;
    private String thumbnailUrl;
    private BigDecimal price;
    private Long stock;

    @Builder
    private ProductDto(Long productId, Long categoryId, String productName, String description, String thumbnailUrl, BigDecimal price, Long stock) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        this.stock = stock;
    }

    public static ProductDto toDto(Product product){
        return ProductDto.builder()
                .categoryId(product.getCategory().getId())
                .productId(product.getId())
                .description(product.getProductDescription())
                .productName(product.getProductName())
                .thumbnailUrl(product.getThumbnailUrl())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
