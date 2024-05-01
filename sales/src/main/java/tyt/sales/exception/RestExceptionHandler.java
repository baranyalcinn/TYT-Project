package tyt.sales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tyt.sales.rules.InsufficientStockException;
import tyt.sales.rules.ProductNotFoundException;

import java.util.Date;

/**
 * This class is responsible for handling exceptions at a global level for the application.
 * It uses the @ControllerAdvice annotation to enable a single ExceptionHandler to be applied to multiple controllers.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * This method handles the InsufficientStockException.
     * It creates an ErrorDetails object with the current date, a message indicating insufficient stock, and the original exception message.
     * It then returns a ResponseEntity with the ErrorDetails object and a HTTP status of BAD_REQUEST.
     *
     * @param ex The InsufficientStockException that was thrown.
     * @return A ResponseEntity containing the ErrorDetails and a HTTP status of BAD_REQUEST.
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorDetails> handleInsufficientStockException(InsufficientStockException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Insufficient Stock",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles the ProductNotFoundException.
     * It creates an ErrorDetails object with the current date, a message indicating the product was not found, and the original exception message.
     * It then returns a ResponseEntity with the ErrorDetails object and a HTTP status of NOT_FOUND.
     *
     * @param ex The ProductNotFoundException that was thrown.
     * @return A ResponseEntity containing the ErrorDetails and a HTTP status of NOT_FOUND.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Product Not Found",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}