package io.github.bhhan.shop.domain;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.common.money.domain.Ratio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    private Shop shop;

    @BeforeEach
    void setUp(){
        shop = Shop.builder()
                .name("김밥천국")
                .minOrderAmount(Money.wons(13000L))
                .commissionRate(Ratio.of(0.2))
                .build();
    }

    @DisplayName("최소주문금액 테스트")
    @Test
    void isValidOrderAmount(){
        assertTrue(shop.isValidOrderAmount(Money.wons(13000L)));
        assertTrue(shop.isValidOrderAmount(Money.wons(16000L)));
        assertFalse(shop.isValidOrderAmount(Money.wons(12900L)));
        assertFalse(shop.isValidOrderAmount(Money.wons(12999L)));
    }
}
