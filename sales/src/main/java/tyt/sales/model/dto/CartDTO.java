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

    private Long id;
    private Long productId;
    private int quantity;
    private String productName;
    private double totalPrice;

    /**
     * This method converts a CartItemEntity object to a CartItemDTO object.
     * It takes a CartItemEntity as input and returns a CartItemDTO.
     *
     * @param cartEntity the CartItemEntity object to be converted
     * @return a CartItemDTO object
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
     * This method converts a list of CartItemEntity objects to a list of CartItemDTO objects.
     * It takes a list of CartItemEntity objects as input and returns a list of CartItemDTO objects.
     *
     * @param cartItems the list of CartItemEntity objects to be converted
     * @return a list of CartItemDTO objects
     */
    public static List<CartDTO> fromEntities(List<CartEntity> cartItems) {
        return cartItems.stream().map(CartDTO::fromEntity).toList();
    }

}