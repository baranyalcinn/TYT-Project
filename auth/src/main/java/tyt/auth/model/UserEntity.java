package tyt.auth.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity{


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
