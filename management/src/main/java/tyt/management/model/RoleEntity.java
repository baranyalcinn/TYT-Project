package tyt.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class RoleEntity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Enumerated(EnumType.STRING)
    private Role roleName;



    public RoleEntity(Role roleName) {
        this.roleName = roleName;
    }

    public RoleEntity(int roleId, Role roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

}
