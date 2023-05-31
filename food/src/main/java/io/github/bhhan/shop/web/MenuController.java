package io.github.bhhan.shop.web;

import io.github.bhhan.shop.usecase.MenuService;
import io.github.bhhan.shop.usecase.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/menus")
public class MenuController {
    private final MenuService menuService;

    @PutMapping("/{menuId}")
    public void updateMenuInfo(@PathVariable Long menuId, @Valid @RequestBody MenuDto.MenuInfoReq menuInfoReq) {
        menuService.updateMenuInfo(menuId, menuInfoReq);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuDto.MenuId addMenu(@Valid @RequestBody MenuDto.MenuReq menuReq) {
        return menuService.addMenu(menuReq);
    }

    @PostMapping("/{menuId}/optionGroupSpecs")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addOptionGroupSpecification(@PathVariable Long menuId, @Valid @RequestBody MenuDto.OptionGroupSpecificationReq optionGroupSpecificationReq) {
        return menuService.addOptionGroupSpecification(menuId, optionGroupSpecificationReq);
    }

    @PostMapping("/{menuId}/optionGroupSpecs/{optionGroupSpecId}/optionSpecs")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addOptionSpecification(@PathVariable Long menuId, @PathVariable Long optionGroupSpecId,
                                          @Valid @RequestBody MenuDto.OptionSpecificationReq optionSpecificationReq) {
        return menuService.addOptionSpecification(menuId, optionGroupSpecId, optionSpecificationReq);
    }

    @PutMapping("/{menuId}/optionGroupSpecs/{optionGroupSpecId}")
    public void updateOptionGroupSpecification(@PathVariable Long menuId, @PathVariable Long optionGroupSpecId,
                                               @Valid @RequestBody MenuDto.OptionGroupSpecificationReq optionGroupSpecificationReq) {
        menuService.updateOptionGroupSpecification(menuId, optionGroupSpecId, optionGroupSpecificationReq);
    }

    @PutMapping("/{menuId}/optionGroupSpecs/{optionGroupSpecId}/optionSpecs/{optionSpecId}")
    public void updateOptionSpecification(@PathVariable Long menuId, @PathVariable Long optionGroupSpecId, @PathVariable Long optionSpecId,
                                          @Valid @RequestBody MenuDto.OptionSpecificationReq optionSpecificationReq) {
        menuService.updateOptionSpecification(menuId, optionGroupSpecId, optionSpecId, optionSpecificationReq);
    }

    @DeleteMapping("/{menuId}")
    public void deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
    }

    @DeleteMapping("/{menuId}/optionGroupSpecs/{optionGroupSpecId}")
    public boolean deleteOptionGroupSpecification(@PathVariable Long menuId, @PathVariable Long optionGroupSpecId) {
        return menuService.deleteOptionGroupSpecification(menuId, optionGroupSpecId);
    }

    @DeleteMapping("/{menuId}/optionGroupSpecs/{optionGroupSpecId}/optionSpecs/{optionSpecId}")
    public boolean deleteOptionSpecification(@PathVariable Long menuId, @PathVariable Long optionGroupSpecId, @PathVariable Long optionSpecId) {
        return menuService.deleteOptionSpecification(menuId, optionGroupSpecId, optionSpecId);
    }
}
