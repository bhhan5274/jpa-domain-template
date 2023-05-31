package io.github.bhhan.delivery.usecase;


import io.github.bhhan.delivery.domain.Delivery;
import io.github.bhhan.delivery.domain.DeliveryRepository;
import io.github.bhhan.order.domain.event.OrderPayedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartDeliveryWithOrderPayedEventHandler {
    private final DeliveryRepository deliveryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = OrderPayedEvent.class, fallbackExecution = true)
    public void handel(OrderPayedEvent event) {
        Delivery delivery = Delivery.started(event.getOrderId());
        deliveryRepository.save(delivery);
        log.info(String.format("OrderPayedEvent[%s] = orderId[%s] >> StartDeliveryWithOrderPayedEventHandler", LocalDateTime.now(), event.getOrderId()));
    }
}
