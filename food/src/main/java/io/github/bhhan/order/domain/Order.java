package io.github.bhhan.order.domain;

import io.github.bhhan.common.money.domain.Money;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", indexes = {
        @Index(columnList = "userId", name = "ix_user_id"),
        @Index(columnList = "shopId", name = "ix_shop_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "update orders set order_status = 'CANCELLED' where order_id = ?", check = ResultCheckStyle.COUNT)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long shopId;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    private LocalDateTime orderedTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Builder
    public Order(Long id, Long userId, Long shopId, List<OrderLineItem> orderLineItems) {
        this.id = id;
        this.userId = userId;
        this.shopId = shopId;
        this.orderLineItems = orderLineItems;
        for (OrderLineItem orderLineItem : orderLineItems) {
            orderLineItem.setOrder(this);
        }
        this.orderStatus = OrderStatus.CREATED;
    }

    public void place(OrderValidator orderValidator) {
        orderValidator.validate(this);
        ordered();
        this.orderedTime = LocalDateTime.now();
    }

    public Money calculateTotalPrice() {
        return Money.sum(orderLineItems, OrderLineItem::calculatePrice);
    }

    public void payed(){
        this.orderStatus = OrderStatus.PAYED;
    }

    public void delivered(){
        this.orderStatus = OrderStatus.DELIVERED;
    }

    public void ordered(){
        this.orderStatus = OrderStatus.ORDERED;
    }
}
