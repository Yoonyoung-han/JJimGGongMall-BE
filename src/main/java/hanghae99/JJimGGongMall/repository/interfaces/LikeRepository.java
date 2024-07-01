package hanghae99.JJimGGongMall.repository.interfaces;

import hanghae99.JJimGGongMall.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByUserIdAndLikeType(Long userId,String likeType);

    void deleteByUserIdAndConnectId(Long userId, Long target);
}
