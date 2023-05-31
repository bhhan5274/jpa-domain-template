package io.github.bhhan.order.domain;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.shop.domain.OptionGroup;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_line_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_item_id")
    private Long id;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_id"))
    private Order order;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "orderLineItem")
    private List<OrderOptionGroup> orderOptionGroups = new ArrayList<>();

    @Builder
    public OrderLineItem(Long id, Long menuId, String name, Integer count, Order order, List<OrderOptionGroup> orderOptionGroups) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
        this.count = count;
        this.orderOptionGroups = orderOptionGroups;
        for (OrderOptionGroup orderOptionGroup : orderOptionGroups) {
            orderOptionGroup.setOrderLineItem(this);
        }
    }

    void setOrder(Order order) {
        this.order = order;
    }

    public Money calculatePrice() {
        return Money.sum(orderOptionGroups, OrderOptionGroup::calculatePrice)
                .times(Double.valueOf(count));
    }

    public List<OptionGroup> convertToOptionGroups() {
        return orderOptionGroups.stream()
                .map(OrderOptionGroup::convertToOptionGroup)
                .collect(Collectors.toList());
    }
}
