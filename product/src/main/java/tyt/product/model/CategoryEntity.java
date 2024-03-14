package tyt.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tyt.product.model.dto.CategoryDTO;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
public class CategoryEntity extends BaseEntity implements Serializable {

    private String name;
    @Column(nullable = false)
    private boolean isActive = true;

    public static CategoryEntity of(CategoryDTO createCategoryRequest) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(createCategoryRequest.getName());
        return categoryEntity;
    }
}
