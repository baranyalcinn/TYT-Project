package tyt.product.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@SQLRestriction("is_active = true")
public class ProductEntity extends BaseEntity {

    @Column(unique = true)
    private String name;

    private String description;

    private double price;

    private int stock;

    @Column(nullable = false)
    private boolean isActive;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}