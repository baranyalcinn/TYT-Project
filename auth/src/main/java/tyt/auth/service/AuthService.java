package tyt.auth.service;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tyt.auth.util.JwtUtils;
import tyt.auth.repository.RoleRepository;
import tyt.auth.model.User;
import tyt.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;


//    @SneakyThrows
//    public String authenticateUser(String username, String password) {
//        User user = userRepository.findByUsername(username);
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            return JwtUtils.generateToken(user);
//        } else {
//            throw new AuthenticationException("Invalid email or password");
//        }
//    }
}

