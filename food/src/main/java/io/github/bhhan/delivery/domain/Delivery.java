package io.github.bhhan.delivery.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "deliveries", indexes = {
        @Index(columnList = "orderId", name = "ix_order_id")
})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;

    public static Delivery started(Long orderId) {
        return Delivery.builder()
                .orderId(orderId)
                .deliveryStatus(DeliveryStatus.DELIVERING)
                .build();
    }

    public void completed(){
        this.deliveryStatus = DeliveryStatus.DELIVERED;
    }
}
