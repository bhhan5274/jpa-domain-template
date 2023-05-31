package io.github.bhhan.order.usecase.dto;

import io.github.bhhan.common.money.domain.Money;
import lombok.*;

import java.util.List;

public class OrderDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderId {
        private Long id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderReq {
        private Long userId;
        private Long shopId;
        private List<OrderLineItemReq> orderLineItemReqList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderLineItemReq {
        private Long menuId;
        private String name;
        private Integer count;
        private List<OrderOptionGroupReq> orderOptionGroupReqList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderOptionGroupReq {
        private String name;
        private List<OrderOptionReq> orderOptionReqList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderOptionReq {
        private String name;
        private Money price;
    }
}
