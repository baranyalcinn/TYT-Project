package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


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
    private LocalDateTime orderDate;

    // Unique number for the order
    private String orderNumber;

    private OfferDTO offer;

}