package tyt.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tyt.product.model.CategoryEntity;
import tyt.product.model.ProductEntity;

import java.util.List;

/**
 * This interface represents the repository for the ProductEntity.
 * It extends JpaRepository which provides JPA related methods like save(), findOne(), findAll(), count(), delete() etc.
 * You can use these methods directly without any implementation.
 *
 * @author baranyalcinn
 * @version 1.0
 * @since 2023.3.5
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Finds a product by its name.
     *
     * @param name The name of the product to be found.
     * @return The product with the given name.
     */
    List<ProductEntity> findByName(String name);

    /**
     * Finds all products in a given category.
     *
     * @param category The category of the products to be found.
     * @return The products in the given category.
     */
    List<ProductEntity> findByCategory(CategoryEntity category);


    /**
     * Finds all products in a given category.
     *
     * @param category The category of the products to be found.
     * @return The products in the given category.
     */
    Page<ProductEntity> findAll(Pageable pageable);
}