package hanghae99.JJimGGongMall.service;

import hanghae99.JJimGGongMall.domain.Category;
import hanghae99.JJimGGongMall.domain.Product;
import hanghae99.JJimGGongMall.dto.CategoryDto;
import hanghae99.JJimGGongMall.repository.interfaces.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .filter(category -> category.getParentId() == 0) // 최상위 카테고리 필터링
                .map(CategoryDto::mapCategoryToDTO) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getSubcategories(Long categoryId) {
        List<Category> categories = categoryRepository.findAllByParentId(categoryId);
        return categories.stream()
                .map(CategoryDto::mapCategoryToDTO)
                .collect(Collectors.toList());
    }

    public String getCategoryHierarchyByProductId(Product product) {
        Long categoryId = product.getCategory().getId();
        return categoryRepository.findCategoryHierarchyByProductId(categoryId);
    }
}
