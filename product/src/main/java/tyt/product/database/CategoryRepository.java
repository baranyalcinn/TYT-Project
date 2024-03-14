package tyt.product.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.product.model.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
