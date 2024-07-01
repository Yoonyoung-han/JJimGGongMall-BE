package hanghae99.JJimGGongMall.repository.interfaces;

import hanghae99.JJimGGongMall.domain.ProductOption;
import hanghae99.JJimGGongMall.domain.ProductOptionCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionCombinationRepository extends JpaRepository<ProductOptionCombination,Long> {
    List<ProductOptionCombination> findByProductId(Long productId);
}
