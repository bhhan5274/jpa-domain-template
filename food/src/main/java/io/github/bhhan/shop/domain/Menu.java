package io.github.bhhan.shop.domain;

import io.github.bhhan.common.money.domain.Money;
import io.github.bhhan.shop.usecase.dto.MenuDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus", indexes = {
        @Index(columnList = "shopId", name = "ix_shop_id"),
        @Index(columnList = "name", unique = true, name = "ux_name")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Long shopId;

    @Version
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "menu")
    @OrderBy("name")
    private List<OptionGroupSpecification> optionGroupSpecs = new ArrayList<>();

    @Builder
    public Menu(Long id, String name, String description, Long shopId,
                List<OptionGroupSpecification> optionGroupSpecs){
        this.id = id;
        this.name = name;
        this.description = description;
        this.shopId = shopId;
        this.optionGroupSpecs.addAll(optionGroupSpecs);
        for (OptionGroupSpecification spec : optionGroupSpecs) {
            spec.setMenu(this);
        }
    }

    public void updateMenuInfo(MenuDto.MenuInfoReq menuInfoReq) {
        this.name = menuInfoReq.getName();
        this.description = menuInfoReq.getDescription();
    }

    public boolean addOptionGroupSpecification(OptionGroupSpecification optionGroupSpecification) {
        optionGroupSpecification.setMenu(this);
        return this.optionGroupSpecs.add(optionGroupSpecification);
    }

    public void updateOptionGroupSpecification(Long optionGroupId, OptionGroupSpecification optionGroupSpecification) {
        OptionGroupSpecification optionGroupSpec = findOptionGroupSpecification(optionGroupId);
        this.optionGroupSpecs.remove(optionGroupSpec);
        optionGroupSpecification.setMenu(this);
        this.optionGroupSpecs.add(optionGroupSpecification);
    }

    public boolean deleteOptionGroupSpecification(Long optionGroupId) {
        OptionGroupSpecification optionGroupSpec = findOptionGroupSpecification(optionGroupId);
        return this.optionGroupSpecs.remove(optionGroupSpec);
    }

    public boolean addOptionSpecification(Long optionGroupId, OptionSpecification optionSpecification) {
        OptionGroupSpecification optionGroupSpec = findOptionGroupSpecification(optionGroupId);
        return optionGroupSpec.addOptionSpecification(optionSpecification);
    }

    public void updateOptionSpecification(Long optionGroupId, Long optionSpecId, OptionSpecification optionSpecification) {
        OptionGroupSpecification optionGroupSpec = findOptionGroupSpecification(optionGroupId);
        optionGroupSpec.updateOptionSpecification(optionSpecId, optionSpecification);
    }

    public boolean deleteOptionSpecification(Long optionGroupId, Long optionSpecId) {
        OptionGroupSpecification optionGroupSpec = findOptionGroupSpecification(optionGroupId);
        return optionGroupSpec.deleteOptionSpecification(optionSpecId);
    }

    public Money getBasePrice() {
        return getBasicOptionGroupSpecs()
                .getOptionSpecs()
                .get(0)
                .getPrice();
    }

    public void validateOrder(String menuName, List<OptionGroup> optionGroups) {
        if (!this.name.equals(menuName)) {
            throw new IllegalArgumentException("기본 상품이 변경됐습니다.");
        }

        if (!isSatisfiedBy(optionGroups)) {
            throw new IllegalArgumentException("메뉴가 정해졌습니다.");
        }
    }

    private OptionGroupSpecification getBasicOptionGroupSpecs() {
        return this.optionGroupSpecs
                .stream()
                .filter(OptionGroupSpecification::isBasic)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isSatisfiedBy(List<OptionGroup> cartOptionGroups) {
        return cartOptionGroups
                .stream()
                .allMatch(this::isSatisfiedBy);
    }

    private boolean isSatisfiedBy(OptionGroup group) {
        return this.optionGroupSpecs
                .stream()
                .anyMatch(spec -> spec.isSatisfiedBy(group));
    }

    private OptionGroupSpecification findOptionGroupSpecification(Long optionGroupId) {
        return this.optionGroupSpecs
                .stream().filter(optionGroupSpec -> optionGroupSpec.getId().equals(optionGroupId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
