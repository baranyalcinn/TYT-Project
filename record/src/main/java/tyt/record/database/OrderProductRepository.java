package tyt.record.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.record.model.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long>{
}
