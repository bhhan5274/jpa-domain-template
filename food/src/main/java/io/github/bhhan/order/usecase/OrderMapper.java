package io.github.bhhan.order.usecase;

import io.github.bhhan.order.domain.Order;
import io.github.bhhan.order.domain.OrderLineItem;
import io.github.bhhan.order.domain.OrderOption;
import io.github.bhhan.order.domain.OrderOptionGroup;
import io.github.bhhan.order.usecase.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public Order fromOrderReq(OrderDto.OrderReq orderReq) {
        return Order.builder()
                .userId(orderReq.getUserId())
                .shopId(orderReq.getShopId())
                .orderLineItems(orderReq.getOrderLineItemReqList().
                        stream()
                        .map(this::fromOrderLineItemReq)
                        .collect(Collectors.toList()))
                .build();
    }

    public OrderLineItem fromOrderLineItemReq(OrderDto.OrderLineItemReq orderLineItemReq) {
        return OrderLineItem.builder()
                .menuId(orderLineItemReq.getMenuId())
                .name(orderLineItemReq.getName())
                .count(orderLineItemReq.getCount())
                .orderOptionGroups(orderLineItemReq.getOrderOptionGroupReqList()
                        .stream()
                        .map(this::fromOrderOptionGroupReq)
                        .collect(Collectors.toList()))
                .build();
    }

    public OrderOptionGroup fromOrderOptionGroupReq(OrderDto.OrderOptionGroupReq orderOptionGroupReq) {
        return OrderOptionGroup.builder()
                .name(orderOptionGroupReq.getName())
                .orderOptions(orderOptionGroupReq.getOrderOptionReqList()
                        .stream()
                        .map(this::fromOrderOptionReq)
                        .collect(Collectors.toList()))
                .build();
    }

    public OrderOption fromOrderOptionReq(OrderDto.OrderOptionReq orderOptionReq) {
        return OrderOption.builder()
                .name(orderOptionReq.getName())
                .price(orderOptionReq.getPrice())
                .build();
    }
}
