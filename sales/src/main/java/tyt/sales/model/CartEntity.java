package tyt.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
public class CartEntity extends BaseEntity implements Serializable {

    @ManyToOne
    private ProductEntity product;
    private int quantity;

    @Transient
    private double totalPrice;

    @Transient
    private String productName;

    @Transient
    private double productPrice;

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
