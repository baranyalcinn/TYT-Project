package tyt.sales.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tyt.sales.model.ProductEntity;

/**
 * This is a Spring Data JPA repository for ProductEntity.
 * It provides methods to perform CRUD operations on the 'product' table in the database.
 * <p>
 * JpaRepository provides JPA related methods such as save(), findOne(), findAll(), count(), delete() etc.
 * You can use these methods directly without any implementation.
 *
 * @Repository makes this class as a Bean in Spring Container. It is a specialization of @Component and is used to denote Data Access Object (DAO) in the application.
 *
 * @author baranyalcinn
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}