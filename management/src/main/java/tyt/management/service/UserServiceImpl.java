package tyt.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tyt.management.database.UserRepository;
import tyt.management.model.UserEntity;
import tyt.management.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public String createUser(UserDTO userDTO) {
        UserEntity entity = UserEntity.of(userDTO);
        UserEntity savedEntity = userRepository.save(entity);

        return savedEntity.getId().toString();
    }
    @Override
    public String updateUser(UserDTO userDTO) {
        Optional<UserEntity> optionalEntity = userRepository.findById(userDTO.getId());

        if (optionalEntity.isPresent()) {
            UserEntity entity = optionalEntity.get();
            entity.update(userDTO);
            UserEntity updatedEntity = userRepository.save(entity);
            return updatedEntity.getId().toString();
        } else {
            throw new RuntimeException("User not found with id: " + userDTO.getId());
        }
    }

    //todo: add findAllByIsActiveTrue
    @Override
    public UserDTO getUser(Long id) {
        Optional<UserEntity> optionalEntity = userRepository.findById(id);

        if (optionalEntity.isPresent()) {
            UserEntity entity = optionalEntity.get();
            return UserDTO.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .surname(entity.getSurname())
                    .email(entity.getEmail())
                    .password(entity.getPassword())
                    .isActive(entity.isActive())
                    .build();
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    @Override
    public void deleteUser(UserDTO userDTO) {
        Optional<UserEntity> optionalEntity = userRepository.findById(userDTO.getId());

        if (optionalEntity.isPresent()) {
            UserEntity entity = optionalEntity.get();
            entity.setActive(false); // Soft delete by setting isActive to false
            userRepository.save(entity);
        } else {
            throw new RuntimeException("User not found with id: " + userDTO.getId());
        }
    }

@Override
public List<UserDTO> getAllUsers() {

    List<UserDTO> allUsers = userRepository.findAll().stream()
            .map(entity -> UserDTO.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .surname(entity.getSurname())
                    .email(entity.getEmail())
                    .password(entity.getPassword())
                    .isActive(entity.isActive())
                    .build())
            .toList();

    return allUsers.stream()
            .filter(UserDTO::isActive)
            .collect(Collectors.toList());
}

    }


