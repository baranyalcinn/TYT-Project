package tyt.management.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tyt.management.model.dto.UserDTO;

import java.io.Serializable;

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

    private Role role;


    public static UserEntity of(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDTO.getName());
        userEntity.setSurname(userDTO.getSurname());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setActive(true);
        userEntity.setRole(userDTO.getRole());
        return userEntity;
    }

    public void update(UserDTO userDTO) {
        this.setName(userDTO.getName());
        this.setSurname(userDTO.getSurname());
        this.setEmail(userDTO.getEmail());
        this.setPassword(userDTO.getPassword());
        this.setRole(userDTO.getRole());
    }

    public void softDelete() {
        this.isActive = false;
    }

}
