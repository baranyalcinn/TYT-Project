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
    private String orderNumber;

    public static OrderDTO fromEntity(OrderEntity orderEntity) {
        return OrderDTO.builder()
                .id(orderEntity.getId())
                .total(orderEntity.getTotal())
                .orderDate(orderEntity.getOrderDate())
                .orderNumber(orderEntity.getOrderNumber() != null ? orderEntity.getOrderNumber().toString() : null)
                .orderProducts(orderEntity.getOrderProducts()
                        != null ? orderEntity.getOrderProducts().stream().map(OrderProductDTO::fromEntity).collect(Collectors.toList()) : null)
                .build();
    }

}