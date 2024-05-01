package tyt.sales.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Data Transfer Object (DTO) for the Product entity.
 * It is used to transfer data between processes or components, in this case, between the service layer and the controller.
 * It includes several annotations such as @Builder, @Data, @NoArgsConstructor, and @AllArgsConstructor from the Lombok library to reduce boilerplate code.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    // Unique identifier for the product
    private Long id;

    // Name of the product
    private String name;

    // Description of the product
    private String description;

    // Price of the product
    private double price;

    // Stock quantity of the product. This field is ignored during serialization/deserialization
    @JsonIgnore
    private int stock;

    // Category name of the product. This field is ignored during serialization/deserialization
    @JsonIgnore
    private String categoryName;

    // Flag indicating if the product is active. This field is ignored during serialization/deserialization
    @JsonIgnore
    private boolean isActive;

    // Identifier for the category of the product
    private Long categoryId;
}