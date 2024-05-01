package tyt.sales.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Represents a request to add a product to a cart.
 * This class is immutable and thread-safe, as it is annotated with @Value from Project Lombok.
 */
@Value
public class CartRequest {

    /**
     * The ID of the product to be added to the cart.
     */
    Long productId;

    /**
     * The quantity of the product to be added to the cart.
     */
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