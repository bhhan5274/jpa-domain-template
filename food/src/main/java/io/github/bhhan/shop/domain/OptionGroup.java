package io.github.bhhan.shop.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionGroup {
    private String name;
    private List<Option> options;
}
