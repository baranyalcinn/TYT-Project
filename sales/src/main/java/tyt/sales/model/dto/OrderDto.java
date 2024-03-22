package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.OrderEntity;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDateTime orderDate;
    private double totalPrice;
    private CartDTO cartItems;

    public static OrderDto fromEntity(OrderEntity orderEntity) {
        return OrderDto.builder()
                .id(orderEntity.getId())
                .orderDate(orderEntity.getOrderDate())
                .totalPrice(orderEntity.getTotalPrice())
                .build();
    }

    public static OrderEntity toEntity(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderDto.getId());
        orderEntity.setOrderDate(orderDto.getOrderDate());
        orderEntity.setTotalPrice(orderDto.getTotalPrice());
        return orderEntity;
    }
}
