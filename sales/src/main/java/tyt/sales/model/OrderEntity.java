package tyt.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "orders")
@Entity
public class OrderEntity extends BaseEntity implements Serializable {

    private LocalDateTime orderDate;
    private double totalPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartEntity> cartItems;

}
