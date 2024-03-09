package tyt.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
public class CategoryEntity extends BaseEntity{

        private String name;
        private String description;
        private boolean isActive = true;


        public void softDelete() {
            this.isActive = false;
        }


        public void setActive(boolean active) {
            isActive = active;
        }

}
