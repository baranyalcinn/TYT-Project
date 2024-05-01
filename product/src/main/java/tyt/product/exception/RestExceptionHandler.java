package tyt.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {
@ExceptionHandler(ProductNotFoundException.class)
public ResponseEntity<ErrorDetails> handleProductNotFoundException(ProductNotFoundException ex) {
    Date timestamp = new Date();
    String message = "Product Not Found";
    String details = ex.getMessage();
    ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details);
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
}

}