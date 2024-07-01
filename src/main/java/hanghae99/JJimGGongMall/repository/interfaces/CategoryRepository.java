package hanghae99.JJimGGongMall.repository.interfaces;

import hanghae99.JJimGGongMall.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByParentId(Long categoryId);

    @Query(value = """
            WITH RECURSIVE CategoryCTE AS (
                SELECT
                    c1.category_id,
                    c1.name,
                    c1.parent_id,
                    c1.depth,
                    c1.name AS path
                FROM
                    Categories c1
                WHERE
                    c1.category_id = :categoryId
                UNION ALL
                SELECT
                    c2.category_id,
                    c2.name,
                    c2.parent_id,
                    c2.depth,
                    CONCAT(CategoryCTE.path, ' > ', c2.name) AS path
                FROM
                    Categories c2
                JOIN
                    CategoryCTE ON c2.parent_id = CategoryCTE.category_id
            )
            SELECT
                path
            FROM
                CategoryCTE
            ORDER BY
                depth DESC
            LIMIT 1;
                """, nativeQuery = true)
    String findCategoryHierarchyByProductId(@Param("categoryId") Long categoryId);

    @Query(value = "WITH RECURSIVE CategoryCTE AS (" +
            "SELECT c.category_id " +
            "FROM Categories c " +
            "WHERE c.category_id = :categoryId " +
            "UNION ALL " +
            "SELECT c2.category_id " +
            "FROM Categories c2 " +
            "JOIN CategoryCTE cte ON cte.category_id = c2.parent_id" +
            ") " +
            "SELECT category_id FROM CategoryCTE", nativeQuery = true)
    List<Long> findAllSubcategoryIds(@Param("categoryId") Long categoryId);
}
