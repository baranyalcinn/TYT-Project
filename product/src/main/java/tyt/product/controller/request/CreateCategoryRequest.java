package tyt.product.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a request to create a new category.
 * It is used to transfer data from the client to the server when a new category is being created.
 *
 * @author baranyalcinn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    /**
     * The name of the category to be created.
     */
    String name;

    /**
     * A boolean flag indicating whether the category to be created is active or not.
     */
    boolean isActive;
}