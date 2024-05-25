package tyt.product.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tyt.product.controller.response.CategoryResponse;
import tyt.product.repository.CategoryRepository;
import tyt.product.exception.Exceptions;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.CategoryDTO;
import tyt.product.model.mapper.CategoryMapper;
import tyt.product.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Category related operations.
 */
@Service
@Log4j2
@AllArgsConstructor
public class CategoryServiceImpl  implements CategoryService {

    // Repository for Category related database operations
    private final CategoryRepository categoryRepository;

    // Mapper for converting between CategoryEntity and CategoryDTO
    private static final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    /**
     * Converts a CategoryEntity to a CategoryDTO.
     * @param categoryEntity The CategoryEntity to be converted.
     * @return The converted CategoryDTO.
     */
    private CategoryDTO toDTO(CategoryEntity categoryEntity) {
        return categoryMapper.toDTO(categoryEntity);
    }

    /**
     * Converts a CategoryDTO to a CategoryEntity.
     * @param categoryDTO The CategoryDTO to be converted.
     * @return The converted CategoryEntity.
     */
    private CategoryEntity toEntity(CategoryDTO categoryDTO) {
        return categoryMapper.toEntity(categoryDTO);
    }

    /**
     * Creates a new category.
     * @param categoryDTO The data transfer object containing the details of the category to be created.
     * @return The ID of the created category.
     * @throws Exceptions.CategoryExistsException if a category with the same name already exists.
     */
    @Override
    public String createCategory(CategoryDTO categoryDTO) {
        CategoryEntity existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategory != null) {
            log.error("Category with name {} already exists", categoryDTO.getName());
            throw new Exceptions.CategoryExistsException("Category with name " + categoryDTO.getName() + " already exists");
        }

        CategoryEntity savedEntity = categoryRepository.save(toEntity(categoryDTO));
        if (savedEntity.getId() == null) {
            log.error("Saved category ID is null");
            throw new RuntimeException("Saved category ID is null");
        }

        CategoryResponse categoryResponse = new CategoryResponse("Category created successfully with ID: " + savedEntity.getId(), HttpStatus.CREATED.value());
        return categoryResponse.getMessage();
    }

    /**
     * Updates an existing category.
     * @param categoryDTO The data transfer object containing the updated details of the category.
     * @return The ID of the updated category.
     * @throws Exceptions.NoSuchCategoryException if no category with the given ID exists.
     */
    @Override
    public String updateCategory(CategoryDTO categoryDTO) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryDTO.getId());
        if (optionalCategoryEntity.isEmpty()) {
            log.error("Category with id {} not found", categoryDTO.getId());
            throw new Exceptions.NoSuchCategoryException("Category with id " + categoryDTO.getId() + " not found");
        }
        CategoryEntity categoryEntity = optionalCategoryEntity.get();

        CategoryEntity updatedEntity = categoryMapper.toEntity(categoryDTO);
        categoryEntity.setName(updatedEntity.getName());
        categoryRepository.save(categoryEntity);

        CategoryResponse categoryResponse = new CategoryResponse("Category updated successfully with ID: " + categoryEntity.getId(), HttpStatus.OK.value());
        return categoryResponse.getMessage();
    }
    /**
     * Deletes a category by setting its active status to false.
     * @param categoryDTO The data transfer object containing the details of the category to be deleted.
     * @throws Exceptions.NoSuchCategoryException if no category with the given ID exists.
     */

    @Override
    public void deleteCategory(CategoryDTO categoryDTO) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryDTO.getId());
        if (optionalCategoryEntity.isEmpty()) {
            log.error("Category with name {} not found", categoryDTO.getName());
            throw new Exceptions.NoSuchCategoryException("Category with id " + categoryDTO.getId() + " not found");
        }
        CategoryEntity categoryEntity = optionalCategoryEntity.get();

        categoryEntity.setActive(false);
        categoryRepository.save(categoryEntity);
    }


    /**
     * Retrieves a category by its ID.
     * @param id The ID of the category to be retrieved.
     * @return The data transfer object of the retrieved category.
     * @throws Exceptions.NoSuchCategoryException if no category with the given ID exists.
     */
    @Override
    public CategoryDTO getCategory(long id) {
        return categoryRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new Exceptions.NoSuchCategoryException("Category with id " + id + " not found"));
    }

    /**
     * Retrieves all active categories.
     * @return A list of data transfer objects of all active categories.
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

}