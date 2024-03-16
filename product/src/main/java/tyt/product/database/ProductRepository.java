package tyt.product.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.product.model.ProductEntity;

/**
 * This interface represents the repository for the ProductEntity.
 * It extends JpaRepository which provides JPA related methods like save(), findOne(), findAll(), count(), delete() etc.
 * You can use these methods directly without any implementation.
 *
 * @author baranyalcinn
 * @version 1.0
 * @since 2023.3.5
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}