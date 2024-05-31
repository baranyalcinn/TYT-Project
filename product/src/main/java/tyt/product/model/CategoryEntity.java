package tyt.product.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
@SQLRestriction("is_active = true")
public class CategoryEntity extends BaseEntity implements Serializable {

    private String name;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ProductEntity> products;

    public boolean getIsActive() {
        return isActive;
    }

}