package tyt.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.sales.model.OfferEntity;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

}
