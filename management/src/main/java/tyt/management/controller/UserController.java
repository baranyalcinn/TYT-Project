package tyt.management.controller;


import lombok.RequiredArgsConstructor;
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
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/getAll")
    public List<UserDTO> getAllActiveUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public String createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(UserMapper.INSTANCE.createRequestToDto(request));
    }


    @PutMapping("/update")
    public String updateUser(@RequestBody UpdateUserRequest request) {
        return userService.updateUser(UserMapper.INSTANCE.updateRequestToDto(request));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setActive(false);
        userService.deleteUser(userDTO);
    }

}
