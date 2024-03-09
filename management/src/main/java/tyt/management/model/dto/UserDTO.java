package tyt.management.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.management.controller.request.CreateUserRequest;
import tyt.management.controller.request.UpdateUserRequest;
import tyt.management.model.Role;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private boolean isActive;
    private Role role;

    public static UserDTO of(CreateUserRequest createUserRequest) {
        return UserDTO.builder()
                .name(createUserRequest.getName())
                .surname(createUserRequest.getSurname())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .role(createUserRequest.getRole())
                .build();
    }

    public static UserDTO of(UpdateUserRequest updateUserRequest) {
        return UserDTO.builder()
                .name(updateUserRequest.getName())
                .surname(updateUserRequest.getSurname())
                .email(updateUserRequest.getEmail())
                .password(updateUserRequest.getPassword())
                .role(updateUserRequest.getRole())
                .build();
    }

    public boolean isActive() {
        return isActive;
    }

}

