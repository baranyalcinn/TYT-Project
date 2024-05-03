package tyt.product.controller.request;

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
    long id;

    /**
     * The new name for the category.
     */
    String name;
    
}