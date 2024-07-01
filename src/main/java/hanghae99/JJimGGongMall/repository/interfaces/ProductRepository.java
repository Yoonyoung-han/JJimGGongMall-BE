package hanghae99.JJimGGongMall.repository.interfaces;

import hanghae99.JJimGGongMall.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository  extends JpaRepository<Product,Long> {
    List<Product> findAllByCategoryIdIn(List<Long> categoryIds);
}
