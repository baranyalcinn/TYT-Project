package tyt.record.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.record.model.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


}
