package tyt.management.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.management.model.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


}
