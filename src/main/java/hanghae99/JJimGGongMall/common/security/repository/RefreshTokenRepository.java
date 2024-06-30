package hanghae99.JJimGGongMall.common.security.repository;

import hanghae99.JJimGGongMall.common.security.entity.RefreshToken;
import hanghae99.JJimGGongMall.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

}
