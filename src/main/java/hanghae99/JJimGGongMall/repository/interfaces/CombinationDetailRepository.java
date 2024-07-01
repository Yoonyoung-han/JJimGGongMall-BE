package hanghae99.JJimGGongMall.repository.interfaces;

import hanghae99.JJimGGongMall.domain.CombinationDetail;
import hanghae99.JJimGGongMall.domain.ProductOptionCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CombinationDetailRepository extends JpaRepository<CombinationDetail, Long> {
    List<CombinationDetail> findByProductOptionCombination(ProductOptionCombination productOptionCombination);
}
