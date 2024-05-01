package tyt.record.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Data Transfer Object (DTO) for the Product entity.
 * It is annotated with Lombok annotations to automatically generate boilerplate code like getters, setters, constructors, and builder pattern methods.
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

    // Price of the product
    private double price;

}