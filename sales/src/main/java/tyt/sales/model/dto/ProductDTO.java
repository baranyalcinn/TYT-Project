package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.ProductEntity;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private boolean isActive;

    private Long categoryId;
    private String categoryName;

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
public static ProductEntity toEntity(ProductDTO productDTO) {
    ProductEntity entity = new ProductEntity();
    entity.setId(productDTO.getId());
    entity.setName(productDTO.getName());
    entity.setDescription(productDTO.getDescription());
    entity.setPrice(productDTO.getPrice());
    entity.setStock(productDTO.getStock());
    entity.setActive(productDTO.isActive());
    return entity;
}


}
