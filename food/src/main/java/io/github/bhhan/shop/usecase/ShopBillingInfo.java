package io.github.bhhan.shop.usecase;

import io.github.bhhan.shop.usecase.dto.ShopDto;

public interface ShopBillingInfo {
    ShopDto.ShopCommissionInfo findCommissionByShopId(Long shopId);
}
