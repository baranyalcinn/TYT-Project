package tyt.management.service;

import org.springframework.stereotype.Service;
import tyt.management.database.UserRepository;
import tyt.management.model.UserEntity;
import tyt.management.model.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(UserDTO userDTO) {
        UserEntity savedEntity = userRepository.save(toEntity(userDTO));
        return savedEntity.getId().toString();
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        UserEntity entity = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getId()));
        entity.update(userDTO);
        UserEntity updatedEntity = userRepository.save(entity);
        return updatedEntity.getId().toString();
    }

    //todo: add findAllByIsActiveTrue
    @Override
    public UserDTO getUser(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toDTO(entity);
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        UserEntity entity = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getId()));
        entity.setActive(false);
        userRepository.save(entity);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(UserEntity::isActive)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.of(userDTO);
    }

    private UserDTO toDTO(UserEntity entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .isActive(entity.isActive())
                .role(entity.getRole())
                .build();
    }

}


