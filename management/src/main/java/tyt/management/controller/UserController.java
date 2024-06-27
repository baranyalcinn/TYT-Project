package tyt.management.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyt.management.controller.request.CreateUserRequest;
import tyt.management.controller.request.UpdateUserRequest;
import tyt.management.model.Role;
import tyt.management.model.dto.UserDTO;
import tyt.management.model.mapper.UserMapper;
import tyt.management.service.UserService;
import tyt.management.util.MissingRequiredRoleException;

import java.util.List;
import java.util.Set;

/**
 * UserController is a REST controller that handles user-related operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Log4j2
public class UserController {

    private final UserService userService;

    /**
     * Get a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the given ID
     */
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {

        log.info("Getting user with id: {}", id);
        return userService.getUser(id);
    }

    /**
     * Get all active users.
     *
     * @return a list of all active users
     */
    @GetMapping("/all")
    public List<UserDTO> getAllActiveUsers() {
        return userService.getAllUsers();
    }

    /**
     * Create a new user.
     *
     * @param request the request containing the user details
     * @return a response entity with the result of the operation
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            validateRoles(request.getRoles());

            String createdUser = userService.createUser(UserMapper.INSTANCE.createRequestToDto(request));
            return ResponseEntity.ok(createdUser);  // Success: Return 200 OK

        } catch (MissingRequiredRoleException ex) {
            return ResponseEntity.badRequest().body("At least one of the Cashier, Manager, Admin roles must be selected");

        } catch (Exception ex) {
            log.error("Error creating user: ", ex);
            return ResponseEntity.badRequest().body("User could not be created."); // Generic error message
        }
    }

    /**
     * Update an existing user.
     *
     * @param request the request containing the updated user details
     * @return a string indicating the result of the operation
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        log.info("Updating user with id: {}", request.getId());
        return userService.updateUser(UserMapper.INSTANCE.updateRequestToDto(request));
    }

    /**
     * Delete a user by their ID.
     *
     * @param id the ID of the user
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        log.info("Deleting user with id: {}", id);
    }

    /**
     * Validate the roles of a user.
     *
     * @param roles the roles of the user
     * @throws MissingRequiredRoleException if the roles are not valid
     */
    private void validateRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty() || !isValidRole(roles)) {
            throw new MissingRequiredRoleException();
        }
    }

    /**
     * Check if the roles are valid.
     *
     * @param roles the roles to check
     * @return true if the roles are valid, false otherwise
     */
    private boolean isValidRole(Set<Role> roles) {
        return roles.stream().anyMatch(
                role -> role.getName().equals("CASHIER") ||
                        role.getName().equals("MANAGER") ||
                        role.getName().equals("ADMIN"));
    }

    /**
     * Handle constraint violation exceptions.
     *
     * @param ex the exception to handle
     * @return a response entity with the result of the operation
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}