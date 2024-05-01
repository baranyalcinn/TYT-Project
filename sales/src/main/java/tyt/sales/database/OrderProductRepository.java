package tyt.sales.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.sales.model.OrderProductEntity;

/**
 * This interface represents a repository for the OrderProductEntity.
 * It extends JpaRepository which provides JPA related methods like save(), findOne(), findAll(), count(), delete() etc.
 * You can use these methods to perform database operations.
 *
 * @author baranyalcinn
 * @version 1.0
 * @since 2024.1
 */
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
}