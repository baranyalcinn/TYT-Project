package tyt.product.controller;

import org.springframework.web.bind.annotation.*;
import tyt.product.controller.request.CreateCategoryRequest;
import tyt.product.controller.request.UpdateCategoryRequest;
import tyt.product.model.dto.CategoryDTO;
import tyt.product.model.mapper.CategoryMapper;
import tyt.product.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CategoryController is a REST controller that handles HTTP requests related to Category.
 * It uses CategoryService to perform operations.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Constructor for CategoryController.
     * @param categoryService The service to be used for category operations.
     */
    public  CategoryController(CategoryService categoryService){
        this.categoryService= categoryService;
    }

    /**
     * Endpoint to create a new category.
     * @param request The request body containing the details of the category to be created.
     * @return A string message indicating the result of the operation.
     */
    @PostMapping("/create")
    public String createCategory(@RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(CategoryMapper.INSTANCE.createRequestToDto(request));
    }


    /**
     * Endpoint to update an existing category.
     * @param request The request body containing the updated details of the category.
     * @return A string message indicating the result of the operation.
     */
    @PutMapping("/update")
    public String updateCategory(@RequestBody UpdateCategoryRequest request){
        return categoryService.updateCategory(CategoryMapper.INSTANCE.updateRequestToDto(request));
    }

    /**
     * Endpoint to delete a category.
     * @param id The id of the category to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id){
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
    @GetMapping("/get")
    public CategoryDTO getCategory(@RequestParam long id){
        return categoryService.getCategory(id);
    }

    /**
     * Endpoint to get the details of all active categories.
     * @return A list of all active categories.
     */
    @GetMapping("/all")
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories().stream()
                .filter(CategoryDTO::isActive)
                .collect(Collectors.toList());
    }

}