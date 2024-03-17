package tyt.product.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.product.model.CategoryEntity;

/**
 * This is the CategoryRepository interface.
 * It extends JpaRepository to leverage Spring Data JPA's methods for database operations.
 * <p>
 * JpaRepository is a JPA specific extension of Repository. It contains the full API of CrudRepository and PagingAndSortingRepository.
 * So it contains API for basic CRUD operations and also API for pagination and sorting.
 *
 * @author baranyalcinn
 * @version 1.0
 * @since 2023.3.5
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}