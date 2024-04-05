package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.CartEntity;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private List<CartItemDTO> cartItems;
    private double totalPrice; // new field

    public static CartDTO fromEntity(CartEntity cartEntity) {
        List<CartItemDTO> cartItemDTOs = cartEntity.getCartItems().stream()
                .map(CartItemDTO::fromEntity)
                .collect(Collectors.toList());

        CartDTO cartDTO = CartDTO.builder()
                .id(cartEntity.getId())
                .cartItems(cartItemDTOs)
                .build();
        // Calculate total price
        double totalPrice = cartDTO.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getProductPrice() * cartItem.getQuantity())
                .sum();
        cartDTO.setTotalPrice(totalPrice);

        return cartDTO;
    }
}