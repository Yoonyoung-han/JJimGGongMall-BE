package hanghae99.JJimGGongMall.dto;

import hanghae99.JJimGGongMall.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryDto {
    private Long categoryId;
    private String name;
    private Long parentId;
    private int depth;
    private List<CategoryDto> children;

    @Builder
    public CategoryDto(Long categoryId, String name, Long parentId, int depth, List<CategoryDto> children) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
        this.children = new ArrayList<>();
    }

    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .categoryId(category.getId())
                .parentId(category.getParentId())
                .depth(category.getDepth())
                .name(category.getName())
                .build();
    }

    public static CategoryDto mapCategoryToDTO(Category category) {
        CategoryDto dto = CategoryDto.of(category);

        // 재귀적으로 하위 카테고리들을 변환
        List<CategoryDto> childrenDTO = category.getChildren().stream()
                .map(CategoryDto::mapCategoryToDTO)
                .collect(Collectors.toList());

        dto.setChildren(childrenDTO);
        return dto;
    }
}