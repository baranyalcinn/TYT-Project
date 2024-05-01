package tyt.product.model.dto;

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
    private String name;

    /**
     * Description of the product.
     */
    private String description;

    /**
     * Price of the product.
     */
    private double price;

    /**
     * Stock quantity of the product.
     */
    private int stock;

    /**
     * Flag indicating whether the product is active or not.
     * By default, it is set to true.
     */
    @Builder.Default
    private boolean isActive = true;

    /**
     * Unique identifier for the category of the product.
     */
    private Long categoryId;

    /**
     * Name of the category of the product.
     */
    private String categoryName;
}