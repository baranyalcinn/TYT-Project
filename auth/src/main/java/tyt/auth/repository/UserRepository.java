package tyt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.auth.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findById(Integer userid);
    UserEntity findByEmail(String email);

}
