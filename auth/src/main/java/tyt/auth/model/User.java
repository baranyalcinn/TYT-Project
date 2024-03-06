package tyt.auth.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity{

    //todo: add @GeneratedValue
    //todo: add Serialization
    //todo: BaseEntity created, all entites extends BaseEntity
    //todo: createdAt, updatedAt, createdBy, updatedBy, deletedAt, deletedBy will be add
    @Setter
    @Id
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    private int roleId;
    private boolean isActive;


}
