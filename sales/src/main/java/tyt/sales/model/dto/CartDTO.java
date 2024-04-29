package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.CartEntity;

import java.util.List;

/**
 * This class represents a Data Transfer Object (DTO) for the CartItemEntity.
 * It includes all the fields from the CartItemEntity, as well as additional fields for product name and price.
 * It also includes methods to convert from CartItemEntity to CartItemDTO and vice versa.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    // Unique identifier for the cart
    private Long id;

    // Unique identifier for the product
    private Long productId;

    // Quantity of the product in the cart
    private int quantity;

    // Name of the product
    private String productName;

    // Total price of the product in the cart
    private double totalPrice;

    /**
     * Converts a CartEntity object to a CartDTO object.
     * @param cartEntity The CartEntity object to be converted.
     * @return A CartDTO object that represents the given CartEntity object.
     */
    public static CartDTO fromEntity(CartEntity cartEntity) {
        return CartDTO.builder()
                .id(cartEntity.getId())
                .productId(cartEntity.getProduct().getId())
                .quantity(cartEntity.getQuantity())
                .productName(cartEntity.getProductName())
                .totalPrice(cartEntity.getTotalPrice())
                .build();
    }

    /**
     * Converts a list of CartEntity objects to a list of CartDTO objects.
     * @param cartItems The list of CartEntity objects to be converted.
     * @return A list of CartDTO objects that represents the given list of CartEntity objects.
     */
    public static List<CartDTO> fromEntities(List<CartEntity> cartItems) {
        return cartItems.stream().map(CartDTO::fromEntity).toList();
    }

}