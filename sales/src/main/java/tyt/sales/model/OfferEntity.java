package tyt.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "offers")
@Entity
public class OfferEntity extends BaseEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private OfferType offerType;
}