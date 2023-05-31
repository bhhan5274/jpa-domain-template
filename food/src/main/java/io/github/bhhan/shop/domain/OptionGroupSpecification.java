package io.github.bhhan.shop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "option_group_specs", indexes = {
        @Index(columnList = "name", unique = true, name = "ux_name")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public class OptionGroupSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_group_spec_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean exclusive;

    @Column(nullable = false)
    private boolean basic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, foreignKey = @ForeignKey(name = "fk_menu_id"))
    private Menu menu;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "optionGroupSpec")
    @OrderBy("name")
    private List<OptionSpecification> optionSpecs = new ArrayList<>();

    @Builder
    public OptionGroupSpecification(Long id, String name, boolean exclusive,
                                    boolean basic,
                                    List<OptionSpecification> optionSpecs) {
        this.id = id;
        this.name = name;
        this.exclusive = exclusive;
        this.basic = basic;
        this.optionSpecs.addAll(optionSpecs);

        for (OptionSpecification option : optionSpecs) {
            option.setOptionGroupSpec(this);
        }
    }

    void setMenu(Menu menu) {
        this.menu = menu;
    }

    public boolean addOptionSpecification(OptionSpecification optionSpecification) {
        optionSpecification.setOptionGroupSpec(this);
        return this.optionSpecs.add(optionSpecification);
    }

    public void updateOptionSpecification(Long optionSpecId, OptionSpecification optionSpecification) {
        this.optionSpecs.remove(findOptionSpec(optionSpecId));
        optionSpecification.setOptionGroupSpec(this);
        this.optionSpecs.add(optionSpecification);
    }

    public boolean deleteOptionSpecification(Long optionSpecId) {
        return this.optionSpecs.remove(findOptionSpec(optionSpecId));
    }

    public boolean isSatisfiedBy(OptionGroup optionGroup) {
        return isSatisfied(optionGroup.getName(), optionGroup.getOptions());
    }

    private OptionSpecification findOptionSpec(Long optionSpecId) {
        return this.optionSpecs
                .stream()
                .filter(optionSpec -> optionSpec.getId().equals(optionSpecId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isSatisfied(String groupName, List<Option> options) {
        if (!this.name.equals(groupName)) {
            return false;
        }

        List<Option> satisfied = this.optionSpecs
                .stream()
                .flatMap(spec -> options
                        .stream()
                        .filter(spec::isSatisfiedBy))
                .collect(Collectors.toList());

        if (satisfied.isEmpty()) {
            return false;
        }

        if (exclusive && satisfied.size() > 1) {
            return false;
        }

        return satisfied.size() == options.size();
    }
}
