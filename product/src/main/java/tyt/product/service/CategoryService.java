package tyt.product.service;

import tyt.product.model.dto.CategoryDTO;

import java.util.List;

/**
 * CategoryService is an interface that defines the operations that can be performed on a Category.
 */
public interface CategoryService {

    /**
     * Creates a new category.
     *
     * @param categoryDTO The data transfer object containing the details of the category to be created.
     * @return A string message indicating the result of the operation.
     */
    String createCategory(CategoryDTO categoryDTO);

    /**
     * Updates an existing category.
     *
     * @param categoryDTO The data transfer object containing the updated details of the category.
     * @return A string message indicating the result of the operation.
     */
    String updateCategory(CategoryDTO categoryDTO);

    /**
     * Deletes a category.
     *
     * @param categoryDTO The data transfer object containing the details of the category to be deleted.
     */
    void deleteCategory(CategoryDTO categoryDTO);

    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to be retrieved.
     * @return The data transfer object of the retrieved category.
     */
    CategoryDTO getCategory(long id);

    /**
     * Retrieves all categories.
     *
     * @return A list of data transfer objects of all categories.
     */
    List<CategoryDTO> getAllCategories();
}