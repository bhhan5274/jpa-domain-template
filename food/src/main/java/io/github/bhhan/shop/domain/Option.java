package io.github.bhhan.shop.domain;

import io.github.bhhan.common.money.domain.Money;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {
    private String name;
    private Money price;
}
