package TYT.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
@SQLRestriction("is_active = true")
public class UserEntity extends BaseEntity implements Serializable {

    private String name;
    private String surname;

    @Column(unique = true)
    @Email
    private String email;

    private String password;

    @Column(nullable = false)
    private boolean isActive = true;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}