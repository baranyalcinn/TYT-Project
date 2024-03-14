package tyt.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.product.controller.request.CreateProductRequest;
import tyt.product.controller.request.UpdateProductRequest;

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
    private long categoryId;


    public static ProductDTO of(CreateProductRequest createProductRequest) {
        return ProductDTO.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .stock(createProductRequest.getStock())
//                .categoryId(createProductRequest.getCategoryId())
                .build();
    }

    public static ProductDTO of(UpdateProductRequest updateProductRequest) {
        return ProductDTO.builder()
                .id(updateProductRequest.getId())
                .name(updateProductRequest.getName())
                .description(updateProductRequest.getDescription())
                .price(updateProductRequest.getPrice())
                .stock(updateProductRequest.getStock())
                .categoryId(updateProductRequest.getCategoryId())
                .build();
    }

//    public static ProductDTO of(CategoryEntity categoryEntity) {
//        return ProductDTO.builder()
//                .categoryId(categoryEntity.getId())
//                .build();
//    }

    public boolean isActive() {
        return isActive;
    }

}
