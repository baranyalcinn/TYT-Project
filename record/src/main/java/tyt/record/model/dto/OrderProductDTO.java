package tyt.record.model.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {
    private Long id; // Unique identifier for the OrderProduct
    private Long orderId; // Identifier for the Order associated with the OrderProduct
    private Long productId; // Identifier for the Product associated with the OrderProduct
    private int quantity; // Quantity of the Product in the Order
    private ProductDTO product; // Product details associated with the OrderProduct


}