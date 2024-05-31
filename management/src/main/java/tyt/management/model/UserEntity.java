package tyt.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Surname is required")
    private String surname;

    @NotEmpty(message = "Email is required")

    @Column(unique = true)
    @Email
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @Column(nullable = false)
    private boolean isActive = true;

    // Represents the roles of the user. It is a set of Role enum.
    // It is eagerly fetched and mapped to "user_role" table in the database.
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Roles are required")
    @Size(min = 1, message = "At least one role must be selected")
    private Set<Role> roles;

}
