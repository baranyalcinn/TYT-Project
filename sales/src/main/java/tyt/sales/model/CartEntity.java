package tyt.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tyt.sales.model.offer.OfferEntity;

import java.io.Serializable;

/**
 * Represents a shopping cart entity in the system.
 * This class extends BaseEntity and implements Serializable interface.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
public class CartEntity extends BaseEntity implements Serializable {

    /**
     * The product entity associated with the cart.
     * It is a many-to-one relationship with the ProductEntity.
     */
    @ManyToOne
    private ProductEntity product;

    /**
     * The quantity of the product in the cart.
     */
    private int quantity;


    private double totalPrice;

    /**
     * The name of the product in the cart.
     * This field is not persisted in the database.
     */
    @Transient
    private String productName;

    /**
     * The price of the product in the cart.
     * This field is not persisted in the database.
     */
    @Transient
    private double productPrice;


    /**
     * The campaign applied to the cart.
     * It is a many-to-one relationship with the CampaignEntity.
     */
    @OneToOne
    private OfferEntity appliedOffer;


}