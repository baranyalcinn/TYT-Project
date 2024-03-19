package tyt.management.service;

import org.springframework.stereotype.Service;
import tyt.management.database.UserRepository;
import tyt.management.model.UserEntity;
import tyt.management.model.dto.UserDTO;
import tyt.management.model.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(UserDTO userDTO) {
        return userRepository.save(userMapper.toEntity(userDTO)).getId().toString();
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        UserEntity entity = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getId()));
        entity.setName(userDTO.getName());
        entity.setSurname(userDTO.getSurname());
        entity.setEmail(userDTO.getEmail());
        entity.setPassword(userDTO.getPassword());
        entity.setRoles(userDTO.getRoles());
        userRepository.save(entity);
        return entity.getId().toString();
    }

    @Override
    public UserDTO getUser(Long id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id)));
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userDTO.getId());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setActive(false);
            userRepository.save(userEntity);
        } else {
            throw new RuntimeException("User not found with id: " + userDTO.getId());
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

}


