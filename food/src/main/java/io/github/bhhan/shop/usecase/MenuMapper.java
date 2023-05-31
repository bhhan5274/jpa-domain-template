package io.github.bhhan.shop.usecase;

import io.github.bhhan.shop.domain.Menu;
import io.github.bhhan.shop.domain.OptionGroupSpecification;
import io.github.bhhan.shop.domain.OptionSpecification;
import io.github.bhhan.shop.usecase.dto.MenuDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {
    public Menu fromMenuReq(MenuDto.MenuReq menuReq) {
        List<OptionGroupSpecification> optionGroupSpecs = menuReq.getAdditives()
                .stream()
                .map(this::fromOptionGroupSpecificationReq)
                .collect(Collectors.toList());

        optionGroupSpecs.add(fromOptionGroupSpecificationReq(menuReq.getBasic()));

        return Menu.builder()
                .name(menuReq.getName())
                .description(menuReq.getDescription())
                .shopId(menuReq.getShopId())
                .optionGroupSpecs(optionGroupSpecs)
                .build();
    }

    public OptionGroupSpecification fromOptionGroupSpecificationReq(MenuDto.OptionGroupSpecificationReq req) {
        return OptionGroupSpecification.builder()
                .name(req.getName())
                .basic(req.getBasic())
                .exclusive(req.getExclusive())
                .optionSpecs(req.getOptionSpecs()
                        .stream()
                        .map(this::fromOptionSpecificationReq)
                        .collect(Collectors.toList()))
                .build();
    }

    public OptionSpecification fromOptionSpecificationReq(MenuDto.OptionSpecificationReq req) {
        return OptionSpecification.builder()
                .name(req.getName())
                .price(req.getPrice())
                .build();
    }
}
