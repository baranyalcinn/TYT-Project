package tyt.sales.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tyt.sales.model.OfferEntity;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

}
