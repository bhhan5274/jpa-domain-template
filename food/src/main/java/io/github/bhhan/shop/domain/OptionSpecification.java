package io.github.bhhan.shop.domain;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.common.money.infra.MoneyConverter;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "option_specs", indexes = {
        @Index(columnList = "name", unique = true, name = "ux_name")
})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public class OptionSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_spec_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Convert(converter = MoneyConverter.class)
    @Column(nullable = false, columnDefinition = "bigint")
    private Money price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_spec_id", nullable = false, foreignKey = @ForeignKey(name = "fk_option_group_spec_id"))
    private OptionGroupSpecification optionGroupSpec;

    void setOptionGroupSpec(OptionGroupSpecification optionGroupSpec) {
        this.optionGroupSpec = optionGroupSpec;
    }

    public boolean isSatisfiedBy(Option option) {
        return Objects.equals(name, option.getName()) && Objects.equals(price, option.getPrice());
    }
}
