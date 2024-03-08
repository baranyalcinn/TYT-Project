package tyt.management.model;


import jakarta.persistence.*;
import lombok.*;
import tyt.management.model.dto.UserDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class UserEntity extends BaseEntity implements Serializable {

    //todo: add @GeneratedValue
    //todo: add Serialization
    //todo: BaseEntity created, all entites ex
    // tends BaseEntity
    //todo: createdAt, updatedAt, createdBy, updatedBy, deletedAt, deletedBy will be add
    private String name;
    private String surname;
    private String email;
    private String password;
    @Column(nullable = false)
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
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
    }

    public void softDelete() {
        this.isActive = false;
    }

}
