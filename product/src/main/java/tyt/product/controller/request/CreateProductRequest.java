package tyt.product.controller.request;

import lombok.Value;

/**
 * This class represents a request to create a product.
 * It is annotated with @Value from the Lombok library, which means it is an immutable class.
 * All fields are private and final, and getters are automatically generated.
 */
@Value
public class CreateProductRequest {
    /**
     * The name of the product.
     */
    String name;

    /**
     * A description of the product.
     */
    String description;

    /**
     * The price of the product.
     */
    double price;

    /**
     * The stock quantity of the product.
     */
    int stock;

    /**
     * The id of the category to which the product belongs.
     */
    Long categoryId;

    /**
     * The name of the category to which the product belongs.
     */
    String categoryName;
}