package tyt.management.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tyt.management.model.UserEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserRepositoryTest {

    @Mock
    UserRepository userRepository;

    UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setId(1L);
        user.setName("baran");
    }

    @Test
    void shouldSaveUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        UserEntity savedUser = userRepository.save(user);
        assertEquals(savedUser.getName(), "baran");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldFindUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        Optional<UserEntity> returnedUser = userRepository.findById(1L);
        assertTrue(returnedUser.isPresent());
        assertEquals(returnedUser.get().getName(), "baran");
    }

    @Test
    void shouldNotFindUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Optional<UserEntity> returnedUser = userRepository.findById(1L);
        assertTrue(returnedUser.isEmpty());
    }

    @Test
    void shouldDeleteUser() {
        doNothing().when(userRepository).delete(user);
        userRepository.delete(user);
        verify(userRepository, times(1)).delete(user);
    }
}