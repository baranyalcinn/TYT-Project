package tyt.sales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tyt.sales.rules.CartIsEmptyException;
import tyt.sales.rules.InsufficientStockException;
import tyt.sales.rules.ProductNotFoundException;
import tyt.sales.rules.ResourceNotFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * This method handles the CartIsEmptyException.
     * It creates an ErrorDetails object with the current date and a message indicating the cart is empty.
     * It then returns a ResponseEntity with the ErrorDetails object and a HTTP status of BAD_REQUEST.
     *
     * @param ex The CartIsEmptyException that was thrown.
     * @return A ResponseEntity containing the ErrorDetails and a HTTP status of BAD_REQUEST.
     */

    @ExceptionHandler(CartIsEmptyException.class)
    public ResponseEntity<ErrorDetails> handleCartIsEmptyException(CartIsEmptyException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Cart is empty",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles the MethodArgumentNotValidException.
     * It creates a map of field names and error messages from the exception.
     * It then returns a ResponseEntity with the map and a HTTP status of BAD_REQUEST.
     *
     * @param ex The MethodArgumentNotValidException that was thrown.
     * @return A ResponseEntity containing the map of field names and error messages and a HTTP status of BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    /**
     * This method handles the ResourceNotFoundException.
     * It creates an ErrorDetails object with the current date, a message indicating the resource was not found, and the original exception message.
     * It then returns a ResponseEntity with the ErrorDetails object and a HTTP status of NOT_FOUND.
     *
     * @param ex The ResourceNotFoundException that was thrown.
     * @return A ResponseEntity containing the ErrorDetails and a HTTP status of NOT_FOUND.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Resource Not Found",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}