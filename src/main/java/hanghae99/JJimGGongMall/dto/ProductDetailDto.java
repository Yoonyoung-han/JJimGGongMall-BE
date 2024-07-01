package hanghae99.JJimGGongMall.dto;

import hanghae99.JJimGGongMall.domain.ProductImage;
import hanghae99.JJimGGongMall.domain.ProductOption;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductDetailDto {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Long stock;
    private String categoryInfo;
    private String thumbnailUrl;
    private List<String> detailImages;
    private List<String> previewImages;
    private List<OptionDto> optionInfo;

    @Builder
    public ProductDetailDto(Long productId, String name, String description, BigDecimal price, Long stock, String categoryInfo,
                            String thumbnailUrl) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryInfo = categoryInfo;
        this.thumbnailUrl = thumbnailUrl;
        this.detailImages = new ArrayList<>(); // 빈 배열로 초기화
        this.previewImages = new ArrayList<>();
        this.optionInfo = new ArrayList<>();
    }

    public void setDetailImages(List<ProductImage> productImage) {
        this.detailImages = toUrlList(productImage);
    }

    public void setPreviewImages(List<ProductImage> productImage) {
        this.previewImages = toUrlList(productImage);
    }

    public void setOptionInfo(List<OptionDto> optionDtoList) {
        this.optionInfo = optionDtoList;
    }

    private List<String> toUrlList(List<ProductImage> productImage){
        return productImage.stream()
                .sorted(Comparator.comparingInt(ProductImage::getOrderIndex)) // orderIndex로 정렬
                .map(ProductImage::getImageUrl) // ProductImage 객체에서 imageUrl 추출
                .collect(Collectors.toList()); // List로 수집
    }
}
