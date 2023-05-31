package io.github.bhhan.shop.usecase.dto;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.common.money.domain.Ratio;
import lombok.*;

public class ShopDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ShopReq {
        private String name;
        private boolean open;
        private Money minOrderAmount;
        private Ratio commissionRate;
    }
}
