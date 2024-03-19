package tyt.management.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.management.model.role.Role;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @Builder.Default
    private boolean isActive = true;
    private Set<Role> roles;


    public boolean isActive() {
        return isActive;
    }

}

