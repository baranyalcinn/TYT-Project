package tyt.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tyt.product.model.CategoryEntity;
import tyt.product.model.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByName(String name);

    List<ProductEntity> findByCategory(CategoryEntity category);

    Page<ProductEntity> findAll(Specification<ProductEntity> spec, Pageable pageable);

}