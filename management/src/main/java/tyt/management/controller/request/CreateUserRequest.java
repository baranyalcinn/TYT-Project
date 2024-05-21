package tyt.management.controller.request;

import lombok.Value;
import tyt.management.model.Role;

import java.util.Set;

/**
 */
@Value
public class CreateUserRequest {
    Set<Role> roles;
    String name;
    String surname;
    String email;
    String password;

}

