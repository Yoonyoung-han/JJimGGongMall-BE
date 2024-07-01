package hanghae99.JJimGGongMall.repository.interfaces;

import hanghae99.JJimGGongMall.domain.Order;
import hanghae99.JJimGGongMall.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);

    Order findByUserAndId(User user, Long orderId);
}
