package tyt.sales.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Data Transfer Object (DTO) for the OrderProduct entity.
 * It is used to transfer data between processes or components, in this case, between the OrderProduct entity and the client.
 * It includes all the fields of the OrderProduct entity.
 * It also includes a static method to convert an OrderProduct entity to an OrderProductDTO.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    // Unique identifier for the OrderProduct
    @JsonIgnore
    private Long id;

    // Identifier for the Order associated with the OrderProduct
    @JsonIgnore
    private Long orderId;

    // Identifier for the Product associated with the OrderProduct
    @JsonIgnore
    private Long productId;

    private String productName;

    // Quantity of the product in the order
    private int quantity;

}