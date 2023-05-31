package io.github.bhhan.shop.usecase.dto;

import io.github.bhhan.common.money.domain.Money;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class MenuDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuReq {

        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Length(max = 30, message = "30자를 넘길 수 없습니다.")
        private String name;
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String description;
        @NotNull(message = "값을 입력하세요.")
        private Long shopId;

        @Valid
        private OptionGroupSpecificationReq basic;
        @Valid
        private List<OptionGroupSpecificationReq> additives;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuId {
        private Long id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OptionGroupSpecificationReq {
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String name;
        @NotNull(message = "값을 입력하세요.")
        private Boolean exclusive;
        @NotNull(message = "값을 입력하세요.")
        private Boolean basic;
        @Valid
        private List<OptionSpecificationReq> optionSpecs;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OptionSpecificationReq {
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String name;
        @Valid
        private Money price;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuInfoReq {
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String name;
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String description;
    }
}
