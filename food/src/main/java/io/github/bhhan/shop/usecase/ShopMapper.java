package io.github.bhhan.shop.usecase;

import io.github.bhhan.shop.domain.Shop;
import io.github.bhhan.shop.usecase.dto.ShopDto;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper {
    public Shop fromShopReq(ShopDto.ShopReq shopReq) {
        return Shop.builder()
                .name(shopReq.getName())
                .open(shopReq.isOpen())
                .minOrderAmount(shopReq.getMinOrderAmount())
                .commissionRate(shopReq.getCommissionRate())
                .build();
    }


}
