    package tyt.management.controller;


    import jakarta.validation.ConstraintViolationException;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.log4j.Log4j2;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.http.converter.HttpMessageNotReadableException;
    import org.springframework.web.bind.annotation.*;
    import tyt.management.controller.request.CreateUserRequest;
    import tyt.management.controller.request.UpdateUserRequest;
    import tyt.management.model.dto.UserDTO;
    import tyt.management.model.mapper.UserMapper;
    import tyt.management.model.role.Role;
    import tyt.management.service.UserService;
    import tyt.management.util.MissingRequiredRoleException;

    import java.util.List;
    import java.util.Set;


    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/user")
    @Log4j2
    public class UserController {

        private final UserService userService;

        @GetMapping("/{id}")
        public UserDTO getUser(@PathVariable Long id) {

            log.info("Getting user with id: {}", id);
            return userService.getUser(id);
        }

        @GetMapping("/all")
        public List<UserDTO> getAllActiveUsers() {
            return userService.getAllUsers();
        }

        @PostMapping("/create")
        public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request) {
            try {
                // Validate roles before calling the service
                validateRoles(request.getRoles());

                String createdUser = userService.createUser(UserMapper.INSTANCE.createRequestToDto(request));
                return ResponseEntity.ok(createdUser);  // Success: Return 200 OK

            } catch (MissingRequiredRoleException ex) {
                // Handle MissingRequiredRoleException
                return ResponseEntity.badRequest().body("At least one of the Cashier, Manager, Admin roles must be selected");

            } catch (Exception ex) {
                // Log the unexpected exception for debugging
                log.error("Error creating user: ", ex);
                return ResponseEntity.badRequest().body("User could not be created."); // Generic error message
            }
        }



        @PutMapping("/update")
        public String updateUser(@RequestBody UpdateUserRequest request) {

            log.info("Updating user with id: {}", request.getId());
            return userService.updateUser(UserMapper.INSTANCE.updateRequestToDto(request));
        }

        @DeleteMapping("/{id}")
        public void deleteUser(@PathVariable Long id) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setActive(false);
            userService.deleteUser(userDTO);
            log.info("Deleting user with id: {}", id);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
            // Get the first violation message
            String message = ex.getConstraintViolations().iterator().next().getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }


        private void validateRoles(Set<Role> roles) {
            if (roles == null || roles.isEmpty() || !isValidRole(roles)) {
                throw new MissingRequiredRoleException();
            }
        }

        private boolean isValidRole(Set<Role> roles) {
            // Since you store String names in your enum, we'll compare against those
            return roles.stream().anyMatch(
                    role -> role.getName().equals("CASHIER") ||
                            role.getName().equals("MANAGER") ||
                            role.getName().equals("ADMIN"));
        }

    }
