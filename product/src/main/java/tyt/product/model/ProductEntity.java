package tyt.product.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;

/**
 * This class represents the Product entity in the database.
 * It extends the BaseEntity class and implements the Serializable interface.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table to be used for mapping.
 * The Lombok annotations @Data, @EqualsAndHashCode and @NoArgsConstructor are used to automatically generate boilerplate code like getters, setters, equals, hashCode and a no-args constructor.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@SQLRestriction("is_active = true")
public class ProductEntity extends BaseEntity implements Serializable {

    // Name of the product
    @Column(unique = true)
    private String name;

    // Description of the product
    private String description;

    // Price of the product
    private double price;

    // Stock quantity of the product
    private int stock;

    // Flag indicating whether the product is active or not
    @Column(nullable = false)
    private boolean isActive = true;

    /**
     * Category of the product.
     * It is a many-to-one relationship with the CategoryEntity.
     * The @JoinColumn annotation specifies the column for joining an entity association or element collection.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}