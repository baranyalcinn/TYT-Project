package tyt.product.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

/**
 * This class represents a request to create a new category.
 * It is used to transfer data from the client to the server when a new category is being created.
 *
 * @author baranyalcinn
 */
@Value
public class CreateCategoryRequest {

    /**
     * The name of the category to be created.
     */
    @NotBlank(message = "Category name cannot be blank")
    @Size(min = 2, max = 30, message = "Category name must be between 2 and 30 characters")
    String name;

    /**
     * A boolean flag indicating whether the category to be created is active or not.
     */
    boolean isActive;
}