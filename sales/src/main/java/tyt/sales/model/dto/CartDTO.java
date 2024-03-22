package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.CartEntity;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long id;
    private int quantity;
    private ProductDTO product;

    public static CartDTO fromEntity(CartEntity cartEntity) {
        return CartDTO.builder()
                .id(cartEntity.getId())
                .quantity(cartEntity.getQuantity())
                .product(ProductDTO.fromEntity(cartEntity.getProduct()))
                .build();
    }

    public static CartEntity toEntity(CartDTO cartDTO) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(cartDTO.getId());
        cartEntity.setQuantity(cartDTO.getQuantity());
        cartEntity.setProduct(ProductDTO.toEntity(cartDTO.getProduct()));
        return cartEntity;
    }
}
