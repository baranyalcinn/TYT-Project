package tyt.product.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Category name cannot be blank")
    @Size(min = 2, max = 30, message = "Category name must be between 2 and 30 characters")
    private String name;

    /**
     * Flag indicating whether the category is active or not.
     * By default, it is set to true.
     */
    @JsonIgnore
    private boolean isActive = true;


    public boolean isActive() {
        return isActive;
    }
}