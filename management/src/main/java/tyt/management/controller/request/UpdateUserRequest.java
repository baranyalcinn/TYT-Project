package tyt.management.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import tyt.management.model.Role;

import java.util.Set;

@Value
public class UpdateUserRequest {

    @NotNull(message = "User Id cannot be blank")
    Long id;
    String name;
    String surname;
    String email;
    String password;
    Set<Role> roles;
}

