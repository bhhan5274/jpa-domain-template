package io.github.bhhan.delivery.usecase;


import io.github.bhhan.delivery.domain.Delivery;
import io.github.bhhan.delivery.domain.DeliveryRepository;
import io.github.bhhan.order.domain.event.OrderDeliveredEvent;
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
public class CompleteDeliveryWithOrderDeliveredEventHandler {
    private final DeliveryRepository deliveryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = OrderDeliveredEvent.class, fallbackExecution = true)
    public void handle(OrderDeliveredEvent event) {
        Delivery delivery = deliveryRepository.findById(event.getOrderId())
                .orElseThrow(IllegalArgumentException::new);

        delivery.completed();
        log.info(String.format("OrderDeliveredEvent[%s] = orderId[%s] >> CompleteDeliveryWithOrderDeliveredEventHandler", LocalDateTime.now(), event.getOrderId()));
    }
}
