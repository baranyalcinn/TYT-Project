package tyt.management.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingRequiredRoleException extends RuntimeException {
    public MissingRequiredRoleException() {
        super("At least one of the Cashier, Manager, Admin roles must be selected");
    }
}
