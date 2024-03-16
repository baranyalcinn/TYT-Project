package tyt.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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


}
