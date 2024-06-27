package tyt.management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tyt.management.model.UserEntity;
import tyt.management.model.dto.UserDTO;
import tyt.management.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserSuccessfully() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        String userId = userService.createUser(userDTO);

        assertEquals("1", userId);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void updateUserSuccessfully() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        ResponseEntity<String> response = userService.updateUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("User with ID: 1 has been successfully updated."));
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void updateUserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userDTO));
    }

    @Test
    void deleteUserSuccessfully() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userEntity));
        UserEntity inactiveUserEntity = new UserEntity();
        inactiveUserEntity.setId(1L);
        inactiveUserEntity.setActive(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(inactiveUserEntity);

        userService.deleteUser(userDTO.getId());

        verify(userRepository, times(1)).findById(userDTO.getId());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void deleteUserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.empty());

        Long userId = userDTO.getId();
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(userId));
    }
}