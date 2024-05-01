package tyt.record.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Order Data Transfer Object (DTO) class.
 * This class is used to transfer data between different parts of the application.
 * It includes all the fields that are needed for an order.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    /**
     * Unique identifier for the order.
     */
    private Long id;

    /**
     * List of products associated with the order.
     */
    private List<OrderProductDTO> orderProducts;

    /**
     * Total cost of the order.
     */
    private double total;

    /**
     * Date when the order was placed.
     */
    private Date orderDate;

    /**
     * Unique number associated with the order.
     */
    private String orderNumber;
}