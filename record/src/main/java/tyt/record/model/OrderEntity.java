package tyt.record.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * This class represents an Order entity in the database.
 * It extends the BaseEntity class and implements the Serializable interface.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table to be used for mapping.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity extends BaseEntity implements Serializable {

    /**
     * This field represents a list of OrderProductEntity objects associated with the OrderEntity.
     * The @OneToMany annotation indicates that an OrderEntity can have multiple OrderProductEntity objects.
     * The mappedBy attribute indicates that the 'order' field in the OrderProductEntity class owns the relationship.
     */
    @OneToMany(mappedBy = "order")
    private List<OrderProductEntity> orderProducts;

    /**
     * This field represents the total cost of the order.
     */
    private double total;

    /**
     * This field represents the date the order was placed.
     */
    private Date orderDate;

    /**
     * This field represents the unique identifier for the order.
     * It is initialized with a random UUID.
     */
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    /**
     * This field represents the OfferEntity associated with the OrderEntity.
     * The @OneToOne annotation indicates that an OrderEntity can have only one OfferEntity.
     * The @JoinTable annotation specifies the name of the join table to be used for mapping.
     * The joinColumns attribute specifies the column in the join table that references the OrderEntity.
     * The inverseJoinColumns attribute specifies the column in the join table that references the OfferEntity.
     */
    @OneToOne
    @JoinTable(
            name = "order_campaign",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private OfferEntity offer;

}