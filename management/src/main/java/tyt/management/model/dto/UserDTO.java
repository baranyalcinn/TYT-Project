package tyt.management.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.management.model.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotNull
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;

    @JsonIgnore
    private boolean isActive;
    private Set<Role> roles;



}

