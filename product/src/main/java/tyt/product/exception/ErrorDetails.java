package tyt.product.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    /**
     * The message describing the error.
     */
    private String message;

    /**
     * Additional details about the error.
     */
    private String details;

    /**
     * The HTTP status code of the error.
     */
    private HttpStatus status;
}