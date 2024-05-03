package tyt.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for handling exceptions at a global level for the REST API.
 * It uses the @ControllerAdvice annotation to provide centralized exception handling across all @RequestMapping methods.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * This method handles the NoSuchProductException.
     * It returns a ResponseEntity with an ErrorDetails object and a HTTP status of NOT_FOUND.
     *
     * @param ex The exception that was thrown.
     * @return ResponseEntity containing the error details and HTTP status.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ErrorDetails> handleProductNotFoundException(NoSuchProductException ex) {
        Date timestamp = new Date();
        String message = "Product Not Found";
        String details = ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles the CategoryExistsException.
     * It returns a ResponseEntity with an ErrorDetails object and a HTTP status of CONFLICT.
     *
     * @param ex The exception that was thrown.
     * @return ResponseEntity containing the error details and HTTP status.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CategoryExistsException.class)
    public ResponseEntity<ErrorDetails> handleCategoryExistsException(CategoryExistsException ex) {
        Date timestamp = new Date();
        String message = "Category Already Exists";
        String details = ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * This method handles the NoSuchCategoryException.
     * It returns a ResponseEntity with an ErrorDetails object and a HTTP status of NOT_FOUND.
     *
     * @param ex The exception that was thrown.
     * @return ResponseEntity containing the error details and HTTP status.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchCategoryException.class)
    public ResponseEntity<ErrorDetails> handleCategoryNotFoundException(NoSuchCategoryException ex) {
        Date timestamp = new Date();
        String message = "Category Not Found";
        String details = ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles the ProductExistException.
     * It returns a ResponseEntity with an ErrorDetails object and a HTTP status of CONFLICT.
     *
     * @param ex The exception that was thrown.
     * @return ResponseEntity containing the error details and HTTP status.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ProductExistException.class)
    public ResponseEntity<ErrorDetails> handleProductExistException(ProductExistException ex) {
        Date timestamp = new Date();
        String message = "Product Already Exists";
        String details = ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}