package io.github.bhhan.shop.usecase;

import io.github.bhhan.shop.domain.Menu;
import io.github.bhhan.shop.domain.OptionGroupSpecification;
import io.github.bhhan.shop.domain.OptionSpecification;
import io.github.bhhan.shop.usecase.dto.MenuDto;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuStructMapper {
    MenuStructMapper INSTANCE = Mappers.getMapper(MenuStructMapper.class);

    @Mapping(target = "optionGroupSpecs", source = "additives", qualifiedByName = "optionGroupSpecs")
    Menu toMenu(MenuDto.MenuReq req);

    @BeforeMapping
    default void beforeMenuReq(MenuDto.MenuReq req) {
        req.getAdditives().add(req.getBasic());
    }

    @Mapping(target = "optionSpecs", source = "optionSpecs", qualifiedByName = "optionSpecs")
    OptionGroupSpecification toOptionGroupSpecification(MenuDto.OptionGroupSpecificationReq req);

    List<OptionGroupSpecification> toOptionGroupSpecificationList(List<MenuDto.OptionGroupSpecificationReq> req);
    OptionSpecification toOptionSpecification(MenuDto.OptionSpecificationReq req);
    List<OptionSpecification> toOptionSpecificationList(List<MenuDto.OptionSpecificationReq> req);

    @Named("optionGroupSpecs")
    default List<OptionGroupSpecification> optionGroupSpecs(List<MenuDto.OptionGroupSpecificationReq> req) {
        return INSTANCE.toOptionGroupSpecificationList(req);
    }

    @Named("optionSpecs")
    default List<OptionSpecification> optionSpecs(List<MenuDto.OptionSpecificationReq> req) {
        return INSTANCE.toOptionSpecificationList(req);
    }
}
