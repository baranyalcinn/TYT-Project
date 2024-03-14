package tyt.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tyt.product.model.dto.ProductDTO;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class ProductEntity extends BaseEntity implements Serializable {

    private String name;
    private String description;
    private double price;
    private int stock;
    @Column(nullable = false)
    private boolean isActive = true;
//    @ManyToOne
//    @JoinColumn(name="categoryId", nullable=false)
//    private CategoryEntity category;


    public static ProductEntity of(ProductDTO createProductRequest) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(createProductRequest.getName());
        productEntity.setDescription(createProductRequest.getDescription());
        productEntity.setPrice(createProductRequest.getPrice());
        productEntity.setStock(createProductRequest.getStock());
//        productEntity.setCategory(createProductRequest.getCategory());
        return productEntity;
    }


}
