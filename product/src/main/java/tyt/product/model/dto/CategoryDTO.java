package tyt.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Category Data Transfer Object (DTO) class.
 * This class is used to transfer data between different parts of the application.
 * It is annotated with Lombok annotations to automatically generate boilerplate code.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    /**
     * Unique identifier for the category.
     */
    private Long id;

    /**
     * Name of the category.
     */
    private String name;

    /**
     * Flag indicating whether the category is active or not.
     * By default, it is set to true.
     */
    @Builder.Default
    private boolean isActive = true;
}