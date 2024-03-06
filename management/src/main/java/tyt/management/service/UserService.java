package tyt.management.service;

import tyt.management.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    String createUser(UserDTO userDTO);
    String updateUser(UserDTO userDTO);
    void deleteUser(UserDTO userDTO);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
}
