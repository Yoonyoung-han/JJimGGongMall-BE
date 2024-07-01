package hanghae99.JJimGGongMall.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "combination_detail")
@Slf4j
public class CombinationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combination_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOption productOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combination_id")
    private ProductOptionCombination productOptionCombination;

}
