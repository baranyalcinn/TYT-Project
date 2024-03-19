package tyt.management.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tyt.management.model.role.Role;

import java.io.Serializable;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class UserEntity extends BaseEntity implements Serializable {

    private String name;
    private String surname;
    private String email;
    private String password;
    @Column(nullable = false)
    private boolean isActive = true;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


}
