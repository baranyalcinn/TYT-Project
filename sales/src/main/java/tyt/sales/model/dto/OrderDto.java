package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.CartEntity;
import tyt.sales.model.OrderEntity;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private double total;
    private Date date;

    public static OrderDto fromEntity(OrderEntity orderEntity) {
        return OrderDto.builder()
                .id(orderEntity.getId())
                .total(orderEntity.getTotal())
                .date(orderEntity.getDate())
                .build();
    }

    public static OrderEntity toEntity(OrderDto orderDto, OrderEntity entity) {
        entity.setId(orderDto.getId());
        entity.setTotal(orderDto.getTotal());
        entity.setDate(orderDto.getDate());
        return entity;
    }


}
