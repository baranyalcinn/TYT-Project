package tyt.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
public class CartItemEntity extends BaseEntity implements Serializable {

    @ManyToOne
    private CartEntity cart;
    @ManyToOne
    private ProductEntity product;
    private int quantity;

    public void setCartId(Long id) {
        this.cart = new CartEntity();
        this.cart.setId(id);
    }

    public void setProductId(Long id) {
        this.product = new ProductEntity();
        this.product.setId(id);
    }

    public Long getCartId() {
        return this.cart.getId();
    }

    public Long getProductId() {
        return this.product.getId();
    }
}
