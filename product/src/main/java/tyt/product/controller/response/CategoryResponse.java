package tyt.product.controller.response;

import lombok.*;

/**
 * This class represents the response from the Category controller.
 * It includes a message and a status code.
 * Lombok annotations are used to automatically generate getters, constructors, and other common methods.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    /**
     * A message that provides more information about the response.
     * This could be a success message, error details, etc.
     */
    private String message;

    /**
     * The HTTP status code associated with the response.
     * This will typically be a standard HTTP status code, like 200 for success or 404 for not found.
     */
    private int statusCode;
}