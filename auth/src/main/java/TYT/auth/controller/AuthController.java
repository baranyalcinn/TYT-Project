package TYT.auth.controller;

import TYT.auth.model.UserEntity;
import TYT.auth.model.dto.AuthRequest;
import TYT.auth.service.JwtServiceImpl;
import TYT.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationProvider authenticationManager;
    private final JwtServiceImpl jwtServiceImpl;


    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtServiceImpl.generateToken((UserEntity) authentication.getPrincipal());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/take")
    public String take() {
        return "take";
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/take2")
    public String take2() {
        return "take";
    }
}