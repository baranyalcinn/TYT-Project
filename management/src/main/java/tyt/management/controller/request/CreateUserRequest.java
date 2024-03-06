package tyt.management.controller.request;

import lombok.Getter;
import lombok.Value;
import tyt.management.model.Role;

import java.util.Set;

/**
 */
@Value
public class CreateUserRequest {
    @Getter
    Role role;
    String name;
    String surname;
    String email;
    String password;

}

