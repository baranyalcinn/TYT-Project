package tyt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
