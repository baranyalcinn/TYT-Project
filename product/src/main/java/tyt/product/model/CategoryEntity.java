package tyt.product.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;
import java.util.List;

/**
 * CategoryEntity class extends BaseEntity and implements Serializable.
 * It represents the category entity in the database.
 * It is annotated with @Entity indicating that it is a JPA entity.
 * Lombok annotations @Data, @EqualsAndHashCode and @NoArgsConstructor are used to automatically generate boilerplate code.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
@SQLRestriction("is_active = true")
public class CategoryEntity extends BaseEntity implements Serializable {

    // Name of the category
    private String name;

    // Boolean field to check if the category is active or not. By default, it is set to true.
    @Setter
    @Column(nullable = false)
    private boolean isActive = true;

    /**
     * List of products associated with the category.
     * It is a one-to-many relationship with the ProductEntity.
     * Mapped by the "category" field in the ProductEntity.
     * Fetch type is LAZY which means it won't be fetched from the database until accessed.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ProductEntity> products;

    public boolean getIsActive() {
        return isActive;
    }

}