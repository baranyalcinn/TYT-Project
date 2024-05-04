package tyt.product.controller.request;

import jakarta.validation.constraints.*;
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
    @NotNull(message = "Product id is required.")
    long id;

    /**
     * The new name of the product.
     */
    @NotBlank(message = "Product name is required.")
    String name;

    /**
     * The new description of the product.
     */
    @NotBlank(message = "Product description is required.")
    String description;

    /**
     * The new price of the product.
     */
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "100000.0", message = "Price must be less than or equal to 100000")
    double price;
    /**
     * The new stock quantity of the product.
     */
    @Positive(message = "Stock must be greater than 0")
    int stock;


    /**
     * The unique identifier of the category to which the product belongs.
     */
    @NotNull(message = "Category id is required.")
    long categoryId;


}