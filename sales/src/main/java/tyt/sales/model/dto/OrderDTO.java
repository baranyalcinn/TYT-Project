package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.OrderEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private List<OrderProductDTO> orderProducts;
    private double total;
    private Date orderDate;

    public static OrderDTO fromEntity(OrderEntity orderEntity) {
        return OrderDTO.builder()
                .id(orderEntity.getId())
                .total(orderEntity.getTotal())
                .orderDate(orderEntity.getOrderDate())
                .orderProducts(orderEntity.getOrderProducts()
                        != null ? orderEntity.getOrderProducts().stream().map(OrderProductDTO::fromEntity).collect(Collectors.toList()) : null)
                .build();
    }

    public static OrderEntity toEntity(OrderDTO orderDto, OrderEntity entity) {
        entity.setId(orderDto.getId());
        entity.setTotal(orderDto.getTotal());
        entity.setOrderDate(orderDto.getOrderDate());
        return entity;
    }
}