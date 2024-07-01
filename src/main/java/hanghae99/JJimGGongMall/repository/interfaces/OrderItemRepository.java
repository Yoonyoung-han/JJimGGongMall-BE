package hanghae99.JJimGGongMall.repository.interfaces;

import hanghae99.JJimGGongMall.domain.Order;
import hanghae99.JJimGGongMall.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);
}
