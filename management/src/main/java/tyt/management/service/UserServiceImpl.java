package tyt.management.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tyt.management.model.UserEntity;
import tyt.management.model.dto.UserDTO;
import tyt.management.model.mapper.UserMapper;
import tyt.management.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * UserServiceImpl is a service class that implements the UserService interface.
 * It provides methods to create, update, retrieve and delete users.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final UserMapper userMapper = UserMapper.INSTANCE;

    /**
     * Creates a new user.
     *
     * @param userDTO Data Transfer Object containing user details.
     * @return The ID of the created user.
     */
    @Override
    public String createUser(UserDTO userDTO) {
        UserEntity userEntity = userMapper.toEntity(userDTO);
        userEntity.setActive(true);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(userEntity).getId().toString();
    }

    /**
     * Updates an existing user.
     *
     * @param userDTO Data Transfer Object containing updated user details.
     * @return The ID of the updated user.
     */
    @Override
    public ResponseEntity<String> updateUser(UserDTO userDTO) {
        UserEntity entity = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userDTO.getId()));
        entity.setName(userDTO.getName());
        entity.setSurname(userDTO.getSurname());
        entity.setEmail(userDTO.getEmail());
        entity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        entity.setRoles(userDTO.getRoles());
        userRepository.save(entity);
        return new ResponseEntity<>("User with ID: " + entity.getId() + " has been successfully updated.", HttpStatus.OK);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A Data Transfer Object containing the user's details.
     */
    @Override
    public UserDTO getUser(Long id) {
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id)));
    }

    /**
     * Deletes a user by setting their active status to false.
     */
    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setActive(false);
            userRepository.save(userEntity);
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }

    /**
     * Retrieves all users.
     *
     * @return A list of Data Transfer Objects containing the details of all users.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

}