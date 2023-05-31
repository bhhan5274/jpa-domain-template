package io.github.bhhan.order.usecase;

import io.github.bhhan.common.event.EventPublisher;
import io.github.bhhan.order.domain.Order;
import io.github.bhhan.order.domain.OrderRepository;
import io.github.bhhan.order.domain.OrderValidator;
import io.github.bhhan.order.domain.event.OrderDeliveredEvent;
import io.github.bhhan.order.domain.event.OrderPayedEvent;
import io.github.bhhan.order.usecase.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final EventPublisher eventPublisher;

    public OrderDto.OrderId placeOrder(OrderDto.OrderReq orderReq) {
        Order order = orderMapper.fromOrderReq(orderReq);
        order.place(orderValidator);
        return new OrderDto.OrderId(orderRepository.save(order).getId());
    }

    public void payOrder(Long orderId) {
        Order order = findOrderById(orderId);
        order.payed();
        eventPublisher.raise(new OrderPayedEvent(orderId));
    }

    public void deliverOrder(Long orderId) {
        Order order = findOrderById(orderId);
        order.delivered();
        eventPublisher.raise(new OrderDeliveredEvent(order));
    }

    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
