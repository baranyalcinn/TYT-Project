package tyt.management.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tyt.management.controller.request.CreateUserRequest;
import tyt.management.controller.request.UpdateUserRequest;
import tyt.management.model.Role;
import tyt.management.model.dto.UserDTO;
import tyt.management.service.UserService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        when(userService.getUser(1L)).thenReturn(userDTO);

        UserDTO result = userController.getUser(1L);

        assertEquals(userDTO, result);
    }

    @Test
    void shouldCreateUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        CreateUserRequest request
                = new CreateUserRequest(roles, "baran", "yalçın", "baranylcn@gmail.com", "password123");
        when(userService.createUser(any())).thenReturn("User created");

        ResponseEntity<String> result = userController.createUser(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User created", result.getBody());
    }

    @Test
    void shouldUpdateUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        UpdateUserRequest request
                = new UpdateUserRequest(1L, "baran", "yalçın", "baran@gmail.com", "password123", roles);
        when(userService.updateUser(any())).thenReturn("User updated");

        String result = userController.updateUser(request);

        assertEquals("User updated", result);
    }

    @Test
    void shouldDeleteUser() {
        userController.deleteUser(1L);
    }
}