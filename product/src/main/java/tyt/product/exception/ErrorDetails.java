package tyt.product.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * This class represents the details of an error that can occur in the application.
 * It includes a timestamp of when the error occurred, a message describing the error,
 * and additional details about the error.
 *
 * @author baranyalcinn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

    /**
     * The timestamp when the error occurred.
     */
    private Date timestamp;

    /**
     * The message describing the error.
     */
    private String message;

    /**
     * Additional details about the error.
     */
    private String details;
}