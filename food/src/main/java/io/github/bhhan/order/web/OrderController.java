package io.github.bhhan.order.web;

import io.github.bhhan.order.usecase.OrderService;
import io.github.bhhan.order.usecase.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto.OrderId addMenu(@Valid @RequestBody OrderDto.OrderReq orderReq) {
        return orderService.placeOrder(orderReq);
    }

    @PostMapping("/{orderId}/payed")
    @ResponseStatus(HttpStatus.OK)
    public void payOrder(@PathVariable Long orderId){
        orderService.payOrder(orderId);
    }

    @PostMapping("/{orderId}/delivered")
    @ResponseStatus(HttpStatus.OK)
    public void deliverOrder(@PathVariable Long orderId){
        orderService.deliverOrder(orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteMenu(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }
}
