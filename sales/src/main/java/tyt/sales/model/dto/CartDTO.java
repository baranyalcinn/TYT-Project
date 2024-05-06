package tyt.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.sales.model.offer.OfferEntity;


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

    private Double productPrice;

    // CartDTO.java
    private OfferEntity appliedOffer;


}