package tyt.sales.controller.request;

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
}