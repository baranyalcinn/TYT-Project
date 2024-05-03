package tyt.product.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product Data Transfer Object (DTO) class.
 * This class is used to transfer data about a product between processes or across network.
 * It is annotated with Lombok annotations to reduce boilerplate code.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    /**
     * Unique identifier for the product.
     */
    private Long id;

    /**
     * Name of the product.
     */
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters")
    private String name;

    /**
     * Description of the product.
     */
    @NotBlank(message = "Product description cannot be blank")
    private String description;

    /**
     * Price of the product.
     */
    @Positive(message = "Product price must be a positive number")
    private double price;

    /**
     * Stock quantity of the product.
     */
    @Min(value = 0, message = "Product stock cannot be less than 0")
    private int stock;

    /**
     * Flag indicating whether the product is active or not.
     * By default, it is set to true.
     */

    @JsonIgnore
    private boolean isActive = true;

    /**
     * Unique identifier for the category of the product.
     */
    private Long categoryId;

    /**
     * Name of the category of the product.
     */
    private String categoryName;


    public boolean isActive() {
        return isActive;
    }
}