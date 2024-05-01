package tyt.product.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class represents the response from the Product controller.
 * It includes a message and a status code.
 *
 * @author baranyalcinn
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    /**
     * The message of the response.
     */
    private String message;

    /**
     * The status code of the response.
     */
    private int statusCode;
}