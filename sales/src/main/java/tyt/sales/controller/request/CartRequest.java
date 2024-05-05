package tyt.sales.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Value;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Represents a request to add a product to a cart.
 * This class is immutable and thread-safe, as it is annotated with @Value from Project Lombok.
 */
@Value
public class CartRequest {

    /**
     * The ID of the product to be added to the cart.
     */

    @NotBlank(message = "Product ID cannot be blank")
    Long productId;

    /**
     * The quantity of the product to be added to the cart.
     */

    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity;

    /**
     * Creates a new CartRequest.
     *
     * @param productId the ID of the product to be added to the cart
     * @param quantity  the quantity of the product to be added to the cart
     */

    @JsonCreator
    public CartRequest(@JsonProperty("productId") Long productId, @JsonProperty("quantity") int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}