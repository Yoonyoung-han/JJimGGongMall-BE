package hanghae99.JJimGGongMall.common.security.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 14400)
public class RefreshToken {

    @Id
    private Long userId;
    private String refreshToken;
}
