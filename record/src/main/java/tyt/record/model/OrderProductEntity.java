package tyt.record.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents the OrderProduct entity in the database.
 * This entity is a part of the "order_product" table.
 * It extends the BaseEntity and implements Serializable interface.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "order_product")
@Entity
public class OrderProductEntity extends BaseEntity implements Serializable {

    /**
     * Represents the ManyToOne relationship with the OrderEntity.
     * The JoinColumn is "order_id".
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    /**
     * Represents the ManyToOne relationship with the ProductEntity.
     * The JoinColumn is "product_id".
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    /**
     * Represents the quantity of the product in the order.
     */
    private int quantity;
}