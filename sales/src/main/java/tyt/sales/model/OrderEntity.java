package tyt.sales.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity extends BaseEntity {

    @OneToMany(mappedBy = "order")
    private List<OrderProductEntity> orderProducts;
    private double total;

    private LocalDateTime orderDate;

    @Column(unique = true)
    private UUID orderNumber = UUID.randomUUID();

    @OneToOne
    @JoinTable(
            name = "order_campaign",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private OfferEntity offer;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderProducts=" + orderProducts.stream().map(OrderProductEntity::getId).toList() +
                ", total=" + total +
                ", orderDate=" + orderDate +
                ", orderNumber=" + orderNumber +
                ", offer=" + (offer != null ? offer.getId() : null) +
                '}';
    }
}