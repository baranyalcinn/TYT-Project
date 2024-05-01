package tyt.record.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ProductEntity class represents the product entity in the database.
 * It extends BaseEntity and implements Serializable interface.
 * It includes fields for product's name, description, price, stock, activity status, category ID and a list of order products.
 */
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

    /**
     * List of OrderProductEntity that represents the orders associated with this product.
     * It is mapped by "product" field in OrderProductEntity.
     */
    @OneToMany(mappedBy = "product")
    private List<OrderProductEntity> orderProducts;
}