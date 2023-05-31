package io.github.bhhan.common.money.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Ratio {

    public static Ratio of(Double rate) {
        return new Ratio(rate);
    }

    @NotNull(message = "값을 입력하세요.")
    private Double rate;

    private Ratio(Double rate) {
        this.rate = rate;
    }

    public Money of(Money price) {
        return price.times(rate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ratio ratio = (Ratio) o;
        return Objects.equals(rate, ratio.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }
}
