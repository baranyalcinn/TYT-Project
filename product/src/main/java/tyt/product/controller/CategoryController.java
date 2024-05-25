package tyt.product.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tyt.product.controller.request.CreateCategoryRequest;
import tyt.product.controller.request.UpdateCategoryRequest;
import tyt.product.model.dto.CategoryDTO;
import tyt.product.model.mapper.CategoryMapper;
import tyt.product.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * CategoryController is a REST controller that handles HTTP requests related to Category.
 * It uses CategoryService to perform operations.
 */
@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    private static final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    /**
     * Endpoint to create a new category.
     * @param request The request body containing the details of the category to be created.
     * @return A string message indicating the result of the operation.
     */
    @PostMapping("/create")
    public String createCategory(@Valid @RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(categoryMapper.createRequestToDto(request));
    }


    /**
     * Endpoint to update an existing category.
     * @param request The request body containing the updated details of the category.
     * @return A string message indicating the result of the operation.
     */
    @PutMapping("/update")
    public String updateCategory(@Valid @RequestBody UpdateCategoryRequest request){
        return categoryService.updateCategory(categoryMapper.updateRequestToDto(request));
    }

    /**
     * Endpoint to delete a category.
     * @param id The id of the category to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@Valid @PathVariable long id){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setActive(false);
        categoryService.deleteCategory(categoryDTO);
    }

    /**
     * Endpoint to get the details of a category.
     * @param id The id of the category to be fetched.
     * @return The details of the category.
     */
    @GetMapping("/get/{id}")
    public CategoryDTO getCategory(@PathVariable long id){
        return categoryService.getCategory(id);
    }

    /**
     * Endpoint to get the details of all active categories.
     * @return A list of all active categories.
     */
    @GetMapping("/all")
    public List<CategoryDTO> getAllCategories(){
        return new ArrayList<>(categoryService.getAllCategories());
    }

}