package tyt.management.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import tyt.management.model.Role;

import java.util.Set;

/**
 *
 */
@Value
public class CreateUserRequest {

    @Min(value = 1, message = "At least one role must be selected")
    Set<Role> roles;

    @NotNull(message = "Name cannot be null")
    String name;

    @NotNull(message = "Surname cannot be null")
    String surname;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    String email;

    @NotNull(message = "Password is required")
    String password;

}

