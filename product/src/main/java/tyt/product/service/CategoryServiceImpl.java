package tyt.product.service;

import org.springframework.stereotype.Service;
import tyt.product.database.CategoryRepository;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.CategoryDTO;
import tyt.product.model.mapper.CategoryMapper;

import java.util.List;

/**
 * Service class for Category related operations.
 */
@Service
public class CategoryServiceImpl  implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;


    private CategoryDTO toDTO(CategoryEntity categoryEntity) {
        return categoryMapper.toDTO(categoryEntity);
    }

    private CategoryEntity toEntity(CategoryDTO categoryDTO) {
        return categoryMapper.toEntity(categoryDTO);
    }

    /**
     * Constructor for CategoryServiceImpl.
     * @param categoryRepository The repository to be used for database operations.
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new category.
     * @param categoryDTO The data transfer object containing the details of the category to be created.
     * @return The ID of the created category.
     */
    @Override
    public String createCategory(CategoryDTO categoryDTO) {
        CategoryEntity savedEntity = categoryRepository.save(toEntity(categoryDTO));
        return savedEntity.getId().toString();
    }

    /**
     * Updates an existing category.
     * @param categoryDTO The data transfer object containing the updated details of the category.
     * @return The ID of the updated category.
     */
    @Override
    public String updateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryDTO.getId()).orElseThrow();
        categoryEntity.setName(categoryDTO.getName());
        categoryRepository.save(categoryEntity);
        return categoryEntity.getId().toString();
    }

    /**
     * Deletes a category by setting its active status to false.
     * @param categoryDTO The data transfer object containing the details of the category to be deleted.
     */
    @Override
    public void deleteCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryDTO.getId()).orElseThrow();
        categoryEntity.setActive(false);
        categoryRepository.save(categoryEntity);
    }

    /**
     * Retrieves a category by its ID.
     * @param id The ID of the category to be retrieved.
     * @return The data transfer object of the retrieved category.
     */
    @Override
    public CategoryDTO getCategory(long id) {
        return toDTO(categoryRepository.findById(id).orElseThrow());
    }

    /**
     * Retrieves all active categories.
     * @return A list of data transfer objects of all active categories.
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .filter(CategoryEntity::isActive)
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}