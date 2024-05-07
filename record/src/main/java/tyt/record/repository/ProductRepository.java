package tyt.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.record.model.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
