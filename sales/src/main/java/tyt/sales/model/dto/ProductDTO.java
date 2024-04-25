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

    /**
     * This static method is used to convert a ProductEntity object to a ProductDTO object.
     * @param productEntity The ProductEntity object to be converted.
     * @return A ProductDTO object.
     */
    public static ProductDTO fromEntity(ProductEntity productEntity) {
        return ProductDTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .isActive(productEntity.isActive())
                .categoryId(productEntity.getCategoryId())
                .build();
    }

    /**
     * This static method is used to convert a ProductDTO object to a ProductEntity object.
     * @param productDTO The ProductDTO object to be converted.
     * @return A ProductEntity object.
     */
    public static ProductEntity toEntity(ProductDTO productDTO) {
        ProductEntity entity = new ProductEntity();
        entity.setId(productDTO.getId());
        entity.setName(productDTO.getName());
        entity.setPrice(productDTO.getPrice());
        entity.setStock(productDTO.getStock());
        return entity;
    }

    /**
     * This static method is used to convert a list of ProductEntity objects to a list of ProductDTO objects.
     * @param products The list of ProductEntity objects to be converted.
     * @return A list of ProductDTO objects.
     */
    public static List<ProductDTO> fromEntities(List<ProductEntity> products) {

        return products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }
}