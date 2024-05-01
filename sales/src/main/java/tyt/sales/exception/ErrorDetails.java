package tyt.sales.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ErrorDetails is a simple POJO class that encapsulates the details of an error.
 * It includes a timestamp, a message, and additional details.
 * This class uses Lombok annotations for automatic generation of getters, setters, constructors, and toString methods.
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
     * The error message.
     */
    private String message;

    /**
     * Additional details about the error.
     */
    private String details;
}