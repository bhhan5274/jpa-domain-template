package io.github.bhhan.shop.usecase;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.common.money.domain.Ratio;
import io.github.bhhan.shop.domain.Shop;
import io.github.bhhan.shop.usecase.dto.ShopDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ShopStructMapperTest {
    @Test
    public void shouldMapShopReqToShop() {
        ShopDto.ShopReq shopReq = ShopDto.ShopReq.builder()
                .name("shop")
                .minOrderAmount(Money.wons(12000L))
                .commissionRate(Ratio.of(1.1))
                .open(true)
                .build();

        Shop shop = ShopStructMapper.INSTANCE.toShop(shopReq);

        assertThat(shop).isNotNull();
        assertThat(shop.getName()).isEqualTo("shop");
        assertThat(shop.getCommissionRate()).isEqualTo(Ratio.of(1.1));
        assertThat(shop.getMinOrderAmount()).isEqualTo(Money.wons(12000L));
        assertThat(shop.isOpen()).isEqualTo(true);
    }
}
