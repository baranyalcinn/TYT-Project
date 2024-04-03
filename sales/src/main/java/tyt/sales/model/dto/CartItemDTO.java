package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.CartItemEntity;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long id;
    private Long cartId;
    private Long productId;
    private int quantity;


    public static CartItemDTO fromEntity(Long id, Long cartId, Long productId, int quantity) {
        return CartItemDTO.builder()
                .id(id)
                .cartId(cartId)
                .productId(productId)
                .quantity(quantity)
                .build();
    }

    public static CartItemEntity toEntity(CartItemDTO cartItemDTO, CartItemEntity entity) {
        entity.setId(cartItemDTO.getId());
        entity.setCartId(cartItemDTO.getCartId());
        entity.setProductId(cartItemDTO.getProductId());
        entity.setQuantity(cartItemDTO.getQuantity());
        return entity;
    }

    public static List<CartItemDTO> fromEntities(List<CartItemEntity> cartItems) {
        return cartItems.stream().map(cartItem -> CartItemDTO.fromEntity(cartItem.getId(), cartItem.getCartId(), cartItem.getProductId(), cartItem.getQuantity())).toList();
    }
}
