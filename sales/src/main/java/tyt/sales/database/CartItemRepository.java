package tyt.sales.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.sales.model.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long>{

}
