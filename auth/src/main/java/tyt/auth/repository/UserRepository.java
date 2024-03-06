package tyt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.auth.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer userid);
    User findByEmail(String email);

}
