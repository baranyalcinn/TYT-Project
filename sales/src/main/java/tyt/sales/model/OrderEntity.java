package tyt.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity extends BaseEntity implements Serializable {

    @ManyToOne
    private CartEntity cart;

    private double total;
    private Date orderDate;


}
