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
    private String productName; // new field
    private double productPrice; // new field


    public static CartItemDTO fromEntity(CartItemEntity cartItemEntity) {
        return CartItemDTO.builder()
                .id(cartItemEntity.getId())
                .cartId(cartItemEntity.getCart().getId())
                .productId(cartItemEntity.getProduct().getId())
                .quantity(cartItemEntity.getQuantity())
                .productName(cartItemEntity.getProductName())
                .productPrice(cartItemEntity.getProductPrice())
                .build();
    }


    public static List<CartItemDTO> fromEntities(List<CartItemEntity> cartItems) {
        return cartItems.stream().map(CartItemDTO::fromEntity).toList();
    }

}