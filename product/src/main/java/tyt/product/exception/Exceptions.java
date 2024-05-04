package tyt.product.exception;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class Exceptions {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetails {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private Date timestamp;
        private String message;
        private String details;
        private HttpStatus status;
    }

    public static class CategoryExistsException extends RuntimeException{
        public CategoryExistsException(String message){
            super(message);
        }
    }

    public static class NoSuchCategoryException extends RuntimeException{
        public NoSuchCategoryException(String message){
            super(message);
        }
    }

    public static class NoSuchProductException extends RuntimeException {
        public NoSuchProductException(String message) {
            super(message);
        }
    }

    public static class ProductExistException extends RuntimeException{
        public ProductExistException(String message){
            super(message);
        }
    }
}