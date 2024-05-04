package tyt.product.exception;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * This class contains all the custom exceptions and error details used in the product module.
 */
public class Exceptions {

    /**
     * This class represents the details of an error.
     * It includes the timestamp of when the error occurred, the error message, details, and the HTTP status.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetails {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private Date timestamp; // The time when the error occurred
        private String message; // The error message
        private String details; // The details of the error
        private HttpStatus status; // The HTTP status of the error
    }

    /**
     * This exception is thrown when a category already exists in the system.
     */
    public static class CategoryExistsException extends RuntimeException{
        public CategoryExistsException(String message){
            super(message);
        }
    }

    /**
     * This exception is thrown when a category does not exist in the system.
     */
    public static class NoSuchCategoryException extends RuntimeException{
        public NoSuchCategoryException(String message){
            super(message);
        }
    }

    /**
     * This exception is thrown when a product does not exist in the system.
     */
    public static class NoSuchProductException extends RuntimeException {
        public NoSuchProductException(String message) {
            super(message);
        }
    }

    /**
     * This exception is thrown when a product already exists in the system.
     */
    public static class ProductExistException extends RuntimeException{
        public ProductExistException(String message){
            super(message);
        }
    }
}