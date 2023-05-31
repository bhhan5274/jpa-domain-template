package io.github.bhhan.order.domain;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.shop.domain.OptionGroup;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_option_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public class OrderOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_option_group_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_line_item_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_line_item_id"))
    private OrderLineItem orderLineItem;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "orderOptionGroup")
    @OrderBy("name")
    private List<OrderOption> orderOptions = new ArrayList<>();


    @Builder
    public OrderOptionGroup(Long id, String name, OrderLineItem orderLineItem, List<OrderOption> orderOptions) {
        this.id = id;
        this.name = name;
        this.orderOptions = orderOptions;
        for (OrderOption orderOption : orderOptions) {
            orderOption.setOrderOptionGroup(this);
        }
    }

    void setOrderLineItem(OrderLineItem orderLineItem) {
        this.orderLineItem = orderLineItem;
    }

    public Money calculatePrice() {
        return Money.sum(orderOptions, OrderOption::getPrice);
    }

    public OptionGroup convertToOptionGroup() {
        return OptionGroup.builder()
                .name(name)
                .options(orderOptions.stream()
                        .map(OrderOption::convertToOption)
                        .collect(Collectors.toList()))
                .build();
    }
}
