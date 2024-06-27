package tyt.management.service;

import org.springframework.http.ResponseEntity;
import tyt.management.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    String createUser(UserDTO userDTO);

    ResponseEntity<String> updateUser(UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO getUser(Long id);

    List<UserDTO> getAllUsers();
}