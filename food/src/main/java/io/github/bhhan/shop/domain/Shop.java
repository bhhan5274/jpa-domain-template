package io.github.bhhan.shop.domain;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.common.money.domain.Ratio;
import io.github.bhhan.common.money.infra.MoneyConverter;
import io.github.bhhan.common.money.infra.RatioConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(columnList = "name", unique = true, name = "ux_name")
}, name = "shops")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private boolean open = false;

    @Convert(converter = MoneyConverter.class)
    @Column(nullable = false, columnDefinition = "bigint")
    private Money minOrderAmount;

    @Convert(converter = RatioConverter.class)
    @Column(nullable = false, columnDefinition = "double")
    private Ratio commissionRate;

    @Version
    private Long version;

    public boolean isValidOrderAmount(Money amount) {
        return amount.isGreaterThanEqual(minOrderAmount);
    }

    public void open() {
        this.open = true;
    }

    public void close() {
        this.open = false;
    }

    public void modifyCommissionRate(Ratio commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Money calculateCommissionFee(Money price) {
        return this.commissionRate.of(price);
    }
}
