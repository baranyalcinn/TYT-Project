package tyt.sales.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class represents the response that will be sent when interacting with the cart.
 * It includes a message and a status code.
 *
 * @author baranyalcinn
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    /**
     * A message that provides information about the operation performed on the cart.
     */
    private String message;

    /**
     * The status code of the operation performed on the cart.
     * This is typically a HTTP status code.
     */
    private int statusCode;
}