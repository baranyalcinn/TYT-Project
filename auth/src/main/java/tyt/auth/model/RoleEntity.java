package tyt.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class RoleEntity {

    @Id
    private String roleId;
    private String roleName;

    public RoleEntity(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

}
