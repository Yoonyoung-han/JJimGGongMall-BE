package hanghae99.JJimGGongMall.dto;

import hanghae99.JJimGGongMall.domain.ProductOption;
import hanghae99.JJimGGongMall.domain.ProductOptionCombination;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OptionDto {
    private Long optionCombinationId;
    private List<Map<String,String>> optionDetails;
    private int stock;
    private BigDecimal price;

    @Builder
    private OptionDto(Long optionCombinationId,List<Map<String, String>> optionDetails, int stock, BigDecimal price) {
        this.optionCombinationId = optionCombinationId;
        this.optionDetails = optionDetails;
        this.stock = stock;
        this.price = price;
    }

    public static OptionDto toDto(ProductOptionCombination combination, List<ProductOption> options){
        List<Map<String, String>> optionInfoList = new ArrayList<>();
        for (ProductOption option : options) {
            Map<String, String> optionInfo = new HashMap<>();
            optionInfo.put(option.getProductOptionName(), option.getProductOptionValue());
            optionInfoList.add(optionInfo);
        }

        return OptionDto.builder()
                .optionCombinationId(combination.getId())
                .optionDetails(optionInfoList)
                .stock(combination.getStock())
                .price(combination.getPrice())
                .build();
    }
}
