package tyt.sales.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

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
    private Long id;
    private String name;
    private String description;
    private double price;
    @JsonIgnore
    private int stock;
    @JsonIgnore
    private String categoryName;
    @JsonIgnore
    private boolean isActive;
    private Long categoryId;


}