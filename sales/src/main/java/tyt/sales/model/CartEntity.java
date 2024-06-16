package tyt.sales.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a shopping cart entity in the system.
 * This class extends BaseEntity and implements Serializable interface.
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
@Entity
public class CartEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity product;

    private int quantity;

    private double totalPrice;

    @Transient
    private String productName;

    @Transient
    private double productPrice;

    @ManyToOne
    private OfferEntity appliedOffer;

    @Override
    public String toString() {
        return "CartEntity{" +
                "product=" + (product != null ? product.getId() : null) +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", appliedOffer=" + (appliedOffer != null ? appliedOffer.getId() : null) +
                '}';
    }
}