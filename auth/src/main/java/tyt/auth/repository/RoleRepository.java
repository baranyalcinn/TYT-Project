package tyt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.auth.model.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

}
