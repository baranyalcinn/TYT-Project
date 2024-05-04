package tyt.record.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

public class Exceptions {
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

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class NoSuchOrderException extends RuntimeException{
        public NoSuchOrderException(String message){
            super(message);
        }
    }
}
