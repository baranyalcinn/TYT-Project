package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.OrderEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a Data Transfer Object (DTO) for the Order entity.
 * It is used to transfer data between processes and can include methods for conversion between entities and DTOs.
 * It is annotated with Lombok annotations to reduce boilerplate code.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    // Unique identifier for the order
    private Long id;

    // List of products in the order
    private List<OrderProductDTO> orderProducts;

    // Total cost of the order
    private double total;

    // Date when the order was placed
    private Date orderDate;

    // Unique number for the order
    private String orderNumber;

    /**
     * This method is used to convert an OrderEntity object into an OrderDTO object.
     * It uses the builder pattern for object creation.
     * @param orderEntity The OrderEntity object to be converted.
     * @return An OrderDTO object that represents the given OrderEntity object.
     */
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