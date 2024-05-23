package tyt.sales.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents the OrderProduct entity.
 * This class extends BaseEntity and implements Serializable interface.
 * It is mapped to the "order_product" table in the database.
 * It contains the details of the products in an order.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "order_product")
@Entity
public class OrderProductEntity extends BaseEntity {

    /**
     * Represents the order to which the product belongs.
     * It is a many-to-one relationship with the OrderEntity.
     * It is joined by the "order_id" column in the "order_product" table.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    /**
     * Represents the product in the order.
     * It is a many-to-one relationship with the ProductEntity.
     * It is joined by the "product_id" column in the "order_product" table.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    /**
     * Represents the quantity of the product in the order.
     */
    private int quantity;
}