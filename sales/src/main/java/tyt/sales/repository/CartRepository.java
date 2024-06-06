package tyt.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import tyt.sales.model.CartEntity;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long>{

    @Transactional
    @Modifying
    void deleteCartItemEntityByProductId(Long productId);

    Optional<CartEntity> findByProductId(Long productId);

}