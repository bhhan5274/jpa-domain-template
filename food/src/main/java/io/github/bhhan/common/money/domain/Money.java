package io.github.bhhan.common.money.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
public class Money {
    public static final Money ZERO = Money.wons(0L);

    public static <T> Money sum(Collection<T> bags, Function<T, Money> monetary) {
        return bags.stream()
                .map(monetary)
                .reduce(Money.ZERO, Money::plus);
    }

    public static Money wons(Long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    @NotNull(message = "값을 입력하세요.")
    private BigDecimal amount;

    private Money(BigDecimal amount){
        this.amount = amount;
    }

    public Money plus(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money minus(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public Money times(Double percent) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
    }

    public Money divide(Double divisor) {
        return new Money(this.amount.divide(BigDecimal.valueOf(divisor), 0, RoundingMode.HALF_DOWN));
    }

    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThanEqual(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }

    public Long longValue() {
        return this.amount.longValue();
    }

    public Double doubleValue() {
        return this.amount.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount.doubleValue(), money.amount.doubleValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return this.amount.toString() + "원";
    }
}
