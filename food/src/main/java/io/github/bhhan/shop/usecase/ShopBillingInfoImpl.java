package io.github.bhhan.shop.usecase;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.bhhan.shop.usecase.dto.ShopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static io.github.bhhan.billing.domain.QBilling.billing;
import static io.github.bhhan.shop.domain.QShop.shop;

@Component
@RequiredArgsConstructor
public class ShopBillingInfoImpl implements ShopBillingInfo {
    private final JPAQueryFactory queryFactory;

    @Override
    public ShopDto.ShopCommissionInfo findCommissionByShopId(Long shopId) {
        return Optional.ofNullable(queryFactory
                        .select(Projections.fields(ShopDto.ShopCommissionInfo.class,
                                shop.id.as("shopId"),
                                shop.name,
                                billing.commission
                        ))
                        .from(shop)
                        .join(billing)
                        .on(shop.id.eq(billing.shopId))
                        .fetchOne())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Shop ID: " + shopId));
    }
}
