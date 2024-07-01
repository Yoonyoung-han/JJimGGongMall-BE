package hanghae99.JJimGGongMall.domain;

import hanghae99.JJimGGongMall.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "Likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    private Long connectId; // 리뷰 ID 또는 상품 ID

    @Column(name = "like_Type")
    private String likeType; // 리뷰 또는 상품 구분

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Like(Long connectId, String likeType, User user) {
        this.connectId = connectId;
        this.likeType = likeType;
        this.user = user;
    }

    // 연관관계 맵핑
    public void setUser(User user) {
        this.user = user;
    }
}