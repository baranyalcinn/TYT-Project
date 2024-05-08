package tyt.record.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents an offer entity in the system.
 * This class extends the BaseEntity class and includes additional fields specific to an offer.
 * It is marked as a JPA entity with the @Entity annotation.
 * The table in the database where the entities will be stored is "offers".
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "offers")
@Entity
public class OfferEntity extends BaseEntity {

    // The name of the offer
    private String name;

    // The type of the offer, represented as an enum
    // The @Enumerated annotation is used to specify that the enum should be persisted as a string
    @Enumerated(EnumType.STRING)
    private OfferType offerType;
}