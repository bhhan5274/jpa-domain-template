package io.github.bhhan.order.usecase;

import io.github.bhhan.order.domain.Order;
import io.github.bhhan.order.domain.OrderLineItem;
import io.github.bhhan.order.domain.OrderValidator;
import io.github.bhhan.shop.usecase.MenuService;
import io.github.bhhan.shop.usecase.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidatorImpl implements OrderValidator {
    private final MenuService menuService;
    private final ShopService shopService;
    @Override
    public void validate(Order order) {
        if(!shopService.isOpen(order.getShopId())) {
            throw new IllegalArgumentException("가게가 영업중이 아닙니다.");
        }

        if(order.getOrderLineItems().isEmpty()) {
            throw new IllegalStateException("주문 항목이 비어 있습니다.");
        }

        if(!shopService.isValidOrderAmount(order.getShopId(), order.calculateTotalPrice())) {
            throw new IllegalStateException("최소 주문 금액 이상을 주문해주세요");
        }

        for (OrderLineItem orderLineItem : order.getOrderLineItems()) {
            menuService.validateOrder(orderLineItem.getMenuId(), orderLineItem.getName(), orderLineItem.convertToOptionGroups());
        }
    }
}
