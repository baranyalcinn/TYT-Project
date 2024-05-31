package tyt.product.service;

import tyt.product.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    String createCategory(CategoryDTO categoryDTO);

    String updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(long id);

    List<CategoryDTO> getAllCategories();
}