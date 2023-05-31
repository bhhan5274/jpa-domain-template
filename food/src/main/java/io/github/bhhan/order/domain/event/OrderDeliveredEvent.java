package io.github.bhhan.order.domain.event;

import io.github.bhhan.common.event.DomainEvent;
import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.order.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class OrderDeliveredEvent implements DomainEvent {
    private Long orderId;
    private Long shopId;
    private Money totalPrice;

    public OrderDeliveredEvent(Order order) {
        this.orderId = order.getId();
        this.shopId = order.getShopId();
        this.totalPrice = order.calculateTotalPrice();
    }
}
