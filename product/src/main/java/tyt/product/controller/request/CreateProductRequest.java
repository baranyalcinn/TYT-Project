package tyt.product.controller.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters")
    String name;

    /**
     * A description of the product.
     */
    @NotBlank(message = "Product description cannot be blank")
    @Size(min = 2, max = 100, message = "Product description must be between 2 and 100 characters")
    String description;

    /**
     * The price of the product.
     */
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "100000.0", message = "Price must be less than or equal to 100000")
    double price;

    /**
     * The stock quantity of the product.
     */
    @Positive(message = "Stock must be greater than 0")
    int stock;

    /**
     * The id of the category to which the product belongs.
     */
    @NotNull(message = "Category id cannot be null")
    Long categoryId;

    /**
     * The name of the category to which the product belongs.
     */
    @NotBlank(message = "Category name cannot be blank")
    String categoryName;
}