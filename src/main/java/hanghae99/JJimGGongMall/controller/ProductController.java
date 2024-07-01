package hanghae99.JJimGGongMall.controller;

import hanghae99.JJimGGongMall.common.ApiResponse;
import hanghae99.JJimGGongMall.dto.ProductDetailDto;
import hanghae99.JJimGGongMall.dto.ProductDto;
import hanghae99.JJimGGongMall.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductDto>> getAllProducts(){
        List<ProductDto> result = productService.getAllProducts();
        return ApiResponse.ok(result);
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductDetailDto> getProductDetail(@PathVariable Long productId){
        ProductDetailDto productDetail = productService.getProductDetail(productId);
        return ApiResponse.ok(productDetail);
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId){
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ApiResponse.ok(products);
    }
}
