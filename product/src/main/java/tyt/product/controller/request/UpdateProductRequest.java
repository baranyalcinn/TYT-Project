package tyt.product.controller.request;

import lombok.Value;

/**
 * This class represents a request to update a product.
 * It is annotated with @Value from the Lombok library, which automatically generates getters, equals, hashCode, toString methods, and a constructor requiring all fields.
 */
@Value
public class UpdateProductRequest {
    /**
     * The unique identifier of the product to be updated.
     */
    long id;

    /**
     * The new name of the product.
     */
    String name;

    /**
     * The new description of the product.
     */
    String description;

    /**
     * The new price of the product.
     */
    double price;

    /**
     * The new stock quantity of the product.
     */
    int stock;

    /**
     * The new active status of the product.
     * If true, the product is active. If false, the product is inactive.
     */
    boolean isActive;

    /**
     * The unique identifier of the category to which the product belongs.
     */
    long categoryId;
}