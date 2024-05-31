package tyt.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.management.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
