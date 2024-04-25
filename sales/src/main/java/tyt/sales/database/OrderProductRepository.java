package tyt.sales.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.sales.model.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
}
