package hanghae99.JJimGGongMall.dto;

import hanghae99.JJimGGongMall.domain.ProductOption;
import lombok.Builder;
import lombok.Data;

@Data
public class OptionDto {
    private String optionName;
    private String optionValue;
    private Long stock;

    @Builder
    private OptionDto(String optionName, String optionValue, Long stock) {
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.stock = stock;
    }

    public static OptionDto toDto(ProductOption productOption){
        return OptionDto.builder()
                .optionName(productOption.getProductOptionName())
                .optionValue(productOption.getProductOptionValue())
                .stock(productOption.getStock())
                .build();
    }
}
