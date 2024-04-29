package tyt.management.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import tyt.management.controller.request.CreateUserRequest;
import tyt.management.controller.request.UpdateUserRequest;
import tyt.management.model.dto.UserDTO;
import tyt.management.model.mapper.UserMapper;
import tyt.management.service.UserService;

import java.util.List;


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

    @GetMapping("/All")
    public List<UserDTO> getAllActiveUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public String createUser(@RequestBody CreateUserRequest request) {
        log.info("Creating user with name: {}", request.getName());
        return userService.createUser(UserMapper.INSTANCE.createRequestToDto(request));
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

}
