package io.github.bhhan.billing.usecase;

import io.github.bhhan.billing.domain.Billing;
import io.github.bhhan.billing.domain.BillingRepository;
import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.order.domain.event.OrderDeliveredEvent;
import io.github.bhhan.shop.usecase.ShopService;
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
public class BillShopWithOrderDeliveredEventHandler {
    private final BillingRepository billingRepository;
    private final ShopService shopService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = OrderDeliveredEvent.class, fallbackExecution = true)
    public void handle(OrderDeliveredEvent event) {
        Billing billing = billingRepository.findById(event.getShopId())
                .orElseGet(() -> billingRepository.save(Billing.builder().shopId(event.getShopId())
                        .commission(Money.ZERO)
                        .build()));

        Money commissionFee = shopService.calculateCommissionFee(event.getShopId(), event.getTotalPrice());
        billing.billCommissionFee(commissionFee);
        log.info(String.format("OrderDeliveredEvent[%s] = orderId[%s] >> BillShopWithOrderDeliveredEventHandler", LocalDateTime.now(), event.getOrderId()));
    }
}
