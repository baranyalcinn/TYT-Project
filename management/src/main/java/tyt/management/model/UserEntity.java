package tyt.management.model;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import tyt.management.model.role.Role;
import tyt.management.util.MissingRequiredRoleException;

import java.io.Serializable;
import java.util.Set;

/**
 * UserEntity class extends BaseEntity and implements Serializable.
 * It represents the "users" table in the database.
 * It includes fields for name, surname, email, password, isActive status and roles.
 * It uses Lombok annotations for automatic generation of getters, setters, equals, hashcode and no-args constructor methods.
 * It uses JPA annotations for ORM mapping.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
@SQLRestriction("is_active = true")
public class UserEntity extends BaseEntity implements Serializable {

    // Represents the name of the user
    @NotEmpty(message = "Name is required")
    private String name;

    // Represents the surname of the user
    @NotEmpty(message = "Surname is required")
    private String surname;

    // Represents the email of the user
    @NotEmpty(message = "Email is required")

    @Column(unique = true)
    @Email
    private String email;

    // Represents the password of the user
    @NotEmpty(message = "Password is required")
    private String password;

    // Represents the active status of the user. By default, it is set to true.
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
