package hanghae99.JJimGGongMall.service;

import hanghae99.JJimGGongMall.domain.Product;
import hanghae99.JJimGGongMall.domain.ProductImage;
import hanghae99.JJimGGongMall.domain.ProductOption;
import hanghae99.JJimGGongMall.dto.OptionDto;
import hanghae99.JJimGGongMall.dto.ProductDetailDto;
import hanghae99.JJimGGongMall.dto.ProductDto;
import hanghae99.JJimGGongMall.repository.interfaces.CategoryRepository;
import hanghae99.JJimGGongMall.repository.interfaces.ProductImageRepository;
import hanghae99.JJimGGongMall.repository.interfaces.ProductOptionRepository;
import hanghae99.JJimGGongMall.repository.interfaces.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

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
        List<ProductOption> productOptions = productOptionRepository.findByProductId(productId);
        List<OptionDto> optionInfoList = productOptions.stream()
                                    .map(OptionDto::toDto)
                                    .toList();

        productDetail.setOptionInfo(optionInfoList);

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
