package tyt.record.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.record.model.ProductEntity;

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
    @JsonIgnore
    private Long categoryId;

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
}
