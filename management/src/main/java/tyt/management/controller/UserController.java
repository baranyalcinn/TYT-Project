package tyt.management.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tyt.management.controller.request.CreateUserRequest;
import tyt.management.model.dto.UserDTO;
import tyt.management.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

     // todo: MapStruct kütüphanesine bak

    @GetMapping("/get/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/getAll")
    public List<UserDTO> getAllActiveUsers() {
        return userService.getAllUsers().stream()
                .filter(UserDTO::isActive)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public String createUser(@Validated @RequestBody CreateUserRequest request) {
        UserDTO userDTO = UserDTO.of(request);
        return userService.createUser(userDTO);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserDTO userDTO;
        userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setActive(false); // Set isActive to false for soft delete
        userService.deleteUser(userDTO);
        return ResponseEntity.noContent().build();
    }

}
