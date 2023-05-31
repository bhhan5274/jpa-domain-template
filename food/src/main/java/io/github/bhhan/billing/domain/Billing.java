package io.github.bhhan.billing.domain;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.common.money.infra.MoneyConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "billings", indexes = {
        @Index(columnList = "shopId", name = "ix_shop_id")
})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billing_id")
    private Long id;

    @Column(nullable = false)
    private Long shopId;

    @Convert(converter = MoneyConverter.class)
    @Column(nullable = false, columnDefinition = "bigint")
    private Money commission;

    public void billCommissionFee(Money commission) {
        this.commission = this.commission.plus(commission);
    }
}
