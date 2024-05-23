package tyt.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ProductEntity class represents the product entity in the database.
 * It extends BaseEntity and implements Serializable interface.
 * It uses Lombok annotations for boilerplate code like getters, setters, equals, and hashCode methods.
 * It is marked as an Entity with the @Entity annotation, and the table it maps to in the database is "products".
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class ProductEntity extends BaseEntity{

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
     * List of OrderProductEntity instances.
     * This represents all the orders that include this product.
     * It is a one-to-many relationship with OrderProductEntity.
     */
    @OneToMany(mappedBy = "product")
    private List<OrderProductEntity> orderProducts;

}