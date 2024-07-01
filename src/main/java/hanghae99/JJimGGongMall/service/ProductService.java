package hanghae99.JJimGGongMall.service;

import hanghae99.JJimGGongMall.domain.*;
import hanghae99.JJimGGongMall.dto.OptionDto;
import hanghae99.JJimGGongMall.dto.ProductDetailDto;
import hanghae99.JJimGGongMall.dto.ProductDto;
import hanghae99.JJimGGongMall.repository.interfaces.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private OptionCombinationRepository optionCombinationRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CombinationDetailRepository combinationDetailRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDetailDto getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : "+ productId));

        ProductDetailDto productDetail = ProductDetailDto.builder()
                .productId(product.getId())
                .price(product.getPrice())
                .name(product.getProductName())
                .thumbnailUrl(product.getThumbnailUrl())
                .description(product.getProductDescription())
                .stock(product.getStock())
                .build();

        // images info
        List<ProductImage> detailImages = productImageRepository.findByProductIdAndIsDetailImageTrue(productId);
        List<ProductImage> previewImages = productImageRepository.findByProductIdAndIsPreviewImageTrue(productId);

        productDetail.setDetailImages(detailImages);
        productDetail.setPreviewImages(previewImages);

        // options info
        // productOptionCombination -> combinationDetail -> options
        // 한 상품에 대한 모든 옵션 조합 가져오기
        List<ProductOptionCombination> combinations = optionCombinationRepository.findByProductId(productId);

        List<OptionDto> optionDtoList = new ArrayList<>();

        for (ProductOptionCombination combination : combinations) {
            List<Long> optionIds = combinationDetailRepository.findByProductOptionCombination(combination).stream()
                    .map(x -> x.getProductOption().getId())
                    .collect(Collectors.toList());

            List<ProductOption> productOptions = productOptionRepository.findByIdIn(optionIds);
            OptionDto optionDto = OptionDto.toDto(combination, productOptions);
            optionDtoList.add(optionDto);
        }

        productDetail.setOptionInfo(optionDtoList);

        // category info
        String categoryInfo = categoryService.getCategoryHierarchyByProductId(product);
        productDetail.setCategoryInfo(categoryInfo);

        return productDetail;
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Long> categoryIds = categoryRepository.findAllSubcategoryIds(categoryId);
        List<Product> products = productRepository.findAllByCategoryIdIn(categoryIds);
        return products.stream().map(ProductDto::toDto).collect(Collectors.toList());
    }
}
