package hanghae99.JJimGGongMall.controller;

import hanghae99.JJimGGongMall.common.ApiResponse;
import hanghae99.JJimGGongMall.dto.CategoryDto;
import hanghae99.JJimGGongMall.service.CategoryService;
import hanghae99.JJimGGongMall.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> result = categoryService.getAllCategories();
        return ApiResponse.ok(result);
    }

    @GetMapping("/{categoryId}/subcategories")
    public ApiResponse<List<CategoryDto>> getSubCategories(@PathVariable Long categoryId){
        List<CategoryDto> result = categoryService.getSubcategories(categoryId);
        return ApiResponse.ok(result);
    }
}
