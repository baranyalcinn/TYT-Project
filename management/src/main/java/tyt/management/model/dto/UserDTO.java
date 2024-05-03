package tyt.management.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.management.model.role.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;

    @JsonIgnore
    private boolean isActive;
    private Set<Role> roles;


    public boolean isActive() {
        return isActive;
    }

}

