package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.CartEntity;
import tyt.sales.model.ProductEntity;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long id;
    private List<CartItemDTO> cartItems;



    public static CartDTO fromEntity(CartEntity cartEntity) {
        return CartDTO.builder()
                .id(cartEntity.getId())
                .cartItems(CartItemDTO.fromEntities(cartEntity.getCartItems()))
                .build();
    }

    public static List<CartDTO> fromEntities(List<CartEntity> cartEntities) {
        return cartEntities.stream().map(CartDTO::fromEntity).toList();
    }

}
