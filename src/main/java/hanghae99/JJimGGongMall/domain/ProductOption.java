package hanghae99.JJimGGongMall.domain;

import hanghae99.JJimGGongMall.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_options")
@Slf4j
public class ProductOption  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @Column(name = "option_name")
    private String productOptionName;

    @Column(name = "option_value")
    private String productOptionValue;
}
