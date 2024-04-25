package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.OrderEntity;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private List<ProductDTO> products;
    private double total;
    private Date orderDate;

    public static OrderDto fromEntity(OrderEntity orderEntity) {
        return OrderDto.builder()
                .id(orderEntity.getId())
                .total(orderEntity.getTotal())
                .orderDate(orderEntity.getOrderDate())
                .products(orderEntity.getProducts() != null ? ProductDTO.fromEntities(orderEntity.getProducts()) : null
                )
                .build();
    }

    public static OrderEntity toEntity(OrderDto orderDto, OrderEntity entity) {
        entity.setId(orderDto.getId());
        entity.setTotal(orderDto.getTotal());
        entity.setOrderDate(orderDto.getOrderDate());
        return entity;
    }


}
