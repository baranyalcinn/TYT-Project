package tyt.auth.service;

import org.springframework.stereotype.Service;
import tyt.auth.model.UserEntity;
import tyt.auth.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findUserById(int userid) {
        return userRepository.findById(userid).orElse(null);
    }

}
