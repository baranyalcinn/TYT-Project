package tyt.sales.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * This class represents an Order entity in the system.
 * It extends the BaseEntity class and implements the Serializable interface.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table to be used for mapping.
 * The Lombok annotations @Data, @EqualsAndHashCode and @NoArgsConstructor are used to automatically generate boilerplate code like getters, setters, equals, hashCode and a no-arguments constructor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity extends BaseEntity implements Serializable {

    /**
     * This field represents a list of OrderProductEntity objects associated with the order.
     * The @OneToMany annotation indicates that an order can have multiple products.
     * The mappedBy attribute indicates that the 'order' field in the OrderProductEntity class owns the relationship.
     */
    @OneToMany(mappedBy = "order")
    private List<OrderProductEntity> orderProducts;

    /**
     * This field represents the total cost of the order.
     */
    private double total;

    /**
     * This field represents the date when the order was placed.
     */
    private LocalDateTime orderDate;

    /**
     * This field represents the unique identifier for the order.
     * It is initialized with a random UUID.
     */
    @Column(unique = true)
    private UUID orderNumber = UUID.randomUUID();

    /**
     * This field represents the list of campaigns associated with the order.
     * The @ManyToMany annotation indicates that an order can be associated with multiple campaigns.
     * The @JoinTable annotation specifies the name of the join table to be used for mapping.
     * The joinColumns attribute specifies the foreign key column in the join table that references the order.
     * The inverseJoinColumns attribute specifies the foreign key column in the join table that references the campaign.
     */
    @OneToOne
    @JoinTable(
            name = "order_campaign",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private OfferEntity offer;


}