package tyt.product.service;

import org.springframework.stereotype.Service;
import tyt.product.database.CategoryRepository;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.CategoryDTO;

import java.util.List;

@Service
public class CategoryServiceImpl  implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String createCategory(CategoryDTO categoryDTO) {
        CategoryEntity savedEntity = categoryRepository.save(toEntity(categoryDTO));
        return savedEntity.getId().toString();
    }

    @Override
    public String updateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryDTO.getId()).orElseThrow();
        categoryEntity.setName(categoryDTO.getName());
        categoryRepository.save(categoryEntity);
        return categoryEntity.getId().toString();

    }

    @Override
    public void deleteCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryDTO.getId()).orElseThrow();
        categoryEntity.setActive(false);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoryDTO getCategory(long id) {
        return toDTO(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .filter(CategoryEntity::isActive)
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    private CategoryEntity toEntity(CategoryDTO categoryDTO) {
        return CategoryEntity.of(categoryDTO);
    }
    private CategoryDTO toDTO(CategoryEntity categoryEntity){
        return CategoryDTO.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .isActive(categoryEntity.isActive())
                .build();
    }
}
