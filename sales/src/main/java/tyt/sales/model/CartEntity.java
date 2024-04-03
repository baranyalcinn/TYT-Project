package tyt.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
public class CartEntity extends BaseEntity implements Serializable {

    @OneToMany(mappedBy = "cart")
    private List<CartItemEntity> cartItems;


}
