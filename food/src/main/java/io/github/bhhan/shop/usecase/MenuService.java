package io.github.bhhan.shop.usecase;

import io.github.bhhan.shop.domain.Menu;
import io.github.bhhan.shop.domain.MenuRepository;
import io.github.bhhan.shop.domain.OptionGroup;
import io.github.bhhan.shop.usecase.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public Menu getMenu(Long menuId) {
        return findMenuById(menuId);
    }

    public MenuDto.MenuId addMenu(MenuDto.MenuReq menuReq){
        return new MenuDto.MenuId(menuRepository.save(menuMapper.fromMenuReq(menuReq)).getId());
    }

    public void deleteMenu(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    public void updateMenuInfo(Long menuId, MenuDto.MenuInfoReq menuInfoReq) {
        Menu menu = findMenuById(menuId);
        menu.updateMenuInfo(menuInfoReq);
    }

    public void validateOrder(Long menuId, String menuName, List<OptionGroup> optionGroups){
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menuId"));

        menu.validateOrder(menuName, optionGroups);
    }

    public boolean addOptionGroupSpecification(Long menuId, MenuDto.OptionGroupSpecificationReq optionGroupSpecificationReq) {
        Menu menu = findMenuFromOptimisticMode(menuId);
        return menu.addOptionGroupSpecification(menuMapper.fromOptionGroupSpecificationReq(optionGroupSpecificationReq));
    }

    public void updateOptionGroupSpecification(Long menuId, Long optionGroupSpecId, MenuDto.OptionGroupSpecificationReq optionGroupSpecificationReq) {
        Menu menu = findMenuFromOptimisticMode(menuId);
        menu.updateOptionGroupSpecification(optionGroupSpecId, menuMapper.fromOptionGroupSpecificationReq(optionGroupSpecificationReq));
    }

    public boolean deleteOptionGroupSpecification(Long menuId, Long optionGroupSpecId) {
        Menu menu = findMenuFromOptimisticMode(menuId);
        return menu.deleteOptionGroupSpecification(optionGroupSpecId);
    }

    public boolean addOptionSpecification(Long menuId, Long optionGroupSpecId, MenuDto.OptionSpecificationReq optionSpecificationReq) {
        Menu menu = findMenuFromOptimisticMode(menuId);
        return menu.addOptionSpecification(optionGroupSpecId, menuMapper.fromOptionSpecificationReq(optionSpecificationReq));
    }

    public void updateOptionSpecification(Long menuId, Long optionGroupSpecId, Long optionSpecId, MenuDto.OptionSpecificationReq optionSpecificationReq) {
        Menu menu = findMenuFromOptimisticMode(menuId);
        menu.updateOptionSpecification(optionGroupSpecId, optionSpecId, menuMapper.fromOptionSpecificationReq(optionSpecificationReq));
    }

    public boolean deleteOptionSpecification(Long menuId, Long optionGroupSpecId, Long optionSpecId) {
        Menu menu = findMenuFromOptimisticMode(menuId);
        return menu.deleteOptionSpecification(optionGroupSpecId, optionSpecId);
    }

    private Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(IllegalArgumentException::new);
    }

    private Menu findMenuFromOptimisticMode(Long menuId) {
        return this.menuRepository.findByIdOptimisticMode(menuId).orElseThrow(() -> new IllegalArgumentException(String.format("Not Found Id: %s", menuId)));
    }
}
