package io.github.bhhan.shop.usecase;

import io.github.bhhan.shop.domain.Shop;
import io.github.bhhan.shop.usecase.dto.ShopDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ShopStructMapper {
    ShopStructMapper INSTANCE = Mappers.getMapper(ShopStructMapper.class);

    Shop toShop(ShopDto.ShopReq shopReq);
    List<Shop> toShopList(List<ShopDto.ShopReq> shopReqs);
}
