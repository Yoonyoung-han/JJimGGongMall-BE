package hanghae99.JJimGGongMall.repository.interfaces;

import hanghae99.JJimGGongMall.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {
    List<ProductOption> findByIdIn(List<Long> optionList);
}
