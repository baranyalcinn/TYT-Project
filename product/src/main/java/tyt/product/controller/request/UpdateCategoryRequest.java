package tyt.product.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * This class represents a request to update a category in the product system.
 * It is annotated with @Value from the Lombok library, which automatically
 * provides getter methods, equals and hashCode implementations, and a toString method.
 */
@Value
public class UpdateCategoryRequest {
    /**
     * The unique identifier of the category to be updated.
     */
    @NotNull(message = "Category ID is required.")
    long id;
    /**
     * The new name for the category.
     */
    @NotBlank(message = "Category name is required.")
    String name;

    @JsonIgnore
    boolean isActive;

}