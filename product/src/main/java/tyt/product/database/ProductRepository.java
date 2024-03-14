package tyt.product.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.product.model.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}