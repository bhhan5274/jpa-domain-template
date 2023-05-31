package io.github.bhhan.common.money.infra;

import io.github.bhhan.common.money.domain.Ratio;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RatioConverter implements AttributeConverter<Ratio, Double> {
    @Override
    public Double convertToDatabaseColumn(Ratio ratio) {
        return ratio.getRate();
    }

    @Override
    public Ratio convertToEntityAttribute(Double rate) {
        return Ratio.of(rate);
    }
}
