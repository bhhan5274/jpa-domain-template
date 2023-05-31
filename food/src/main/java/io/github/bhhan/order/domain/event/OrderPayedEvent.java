package io.github.bhhan.order.domain.event;

import io.github.bhhan.common.event.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderPayedEvent implements DomainEvent {
    private Long orderId;

    public OrderPayedEvent(Long orderId) {
        this.orderId = orderId;
    }
}
