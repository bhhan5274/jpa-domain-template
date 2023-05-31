package io.github.bhhan.order.domain;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.common.money.infra.MoneyConverter;
import io.github.bhhan.shop.domain.Option;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_options")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public class OrderOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_option_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Convert(converter = MoneyConverter.class)
    @Column(nullable = false, columnDefinition = "bigint")
    private Money price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_option_group_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_option_group_id"))
    private OrderOptionGroup orderOptionGroup;

    void setOrderOptionGroup(OrderOptionGroup orderOptionGroup) {
        this.orderOptionGroup = orderOptionGroup;
    }

    public Option convertToOption() {
        return Option.builder()
                .name(name)
                .price(price)
                .build();
    }
}
