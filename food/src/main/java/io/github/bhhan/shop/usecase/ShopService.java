package io.github.bhhan.shop.usecase;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.shop.domain.Shop;
import io.github.bhhan.shop.domain.ShopRepository;
import io.github.bhhan.shop.usecase.dto.ShopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    public Long addShop(ShopDto.ShopReq shopReq) {
        Shop shop = ShopStructMapper.INSTANCE.toShop(shopReq);
        return shopRepository.save(shop).getId();
    }

    public boolean isValidOrderAmount(Long shopId, Money amount) {
        Shop shop = findByShopId(shopId);
        return shop.isValidOrderAmount(amount);
    }

    public void shopOpen(Long shopId) {
        findByShopId(shopId).open();
    }

    public void shopClose(Long shopId) {
        findByShopId(shopId).close();
    }

    public Money calculateCommissionFee(Long shopId, Money price){
        return findByShopId(shopId).calculateCommissionFee(price);
    }

    public boolean isOpen(Long shopId){
        return findByShopId(shopId).isOpen();
    }

    private Shop findByShopId(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shopId"));
    }
}
