package tyt.record.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "products")
@Entity
public class ProductEntity extends BaseEntity implements Serializable {
    // Name of the product
    private String name;

    // Description of the product
    private String description;

    // Price of the product
    private double price;

    // Stock quantity of the product
    private int stock;

    // Flag to indicate if the product is active or not
    private boolean isActive;

    // Category ID to which the product belongs
    private Long categoryId;

    @OneToMany(mappedBy = "product")
    private List<OrderProductEntity> orderProducts;
}