package io.github.bhhan.shop.domain;

import io.github.bhhan.common.money.domain.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    public static final long BASE_AMOUNT = 10000L;
    private Menu menu;

    @BeforeEach
    void init() {
        menu = Menu.builder()
                .name("불고기 버거")
                .description("불고기 버거")
                .optionGroupSpecs(List.of(
                        OptionGroupSpecification.builder()
                                .name("기본")
                                .basic(true)
                                .optionSpecs(
                                        List.of(
                                                OptionSpecification.builder()
                                                        .name("기본")
                                                        .price(Money.wons(BASE_AMOUNT))
                                                        .build()
                                        )
                                )
                                .build()
                )).build();
    }

    @DisplayName("기본금액 체크")
    @Test
    void getBasePriceTest() {
        assertEquals(BASE_AMOUNT, menu.getBasePrice().longValue());
    }

    @DisplayName("메뉴 이름 불일치")
    @Test
    void validateOrderFail() {
        assertThrows(IllegalArgumentException.class, () -> menu.validateOrder("새우 버거", null));
    }
}
