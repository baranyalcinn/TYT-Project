package tyt.record.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.record.model.OrderProductEntity;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;

    public static OrderProductDTO fromEntity(OrderProductEntity orderProductEntity) {
        return OrderProductDTO.builder()
                .id(orderProductEntity.getId())
                .orderId(orderProductEntity.getOrder().getId())
                .productId(orderProductEntity.getProduct().getId())
                .quantity(orderProductEntity.getQuantity())
                .build();
    }
}
