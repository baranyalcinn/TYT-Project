package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.OrderProductEntity;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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