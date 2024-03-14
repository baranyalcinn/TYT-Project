package tyt.product.controller;


import org.springframework.web.bind.annotation.*;
import tyt.product.controller.request.CreateCategoryRequest;
import tyt.product.model.dto.CategoryDTO;
import tyt.product.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public  CategoryController(CategoryService categoryService){
        this.categoryService= categoryService;
    }

    @PostMapping("/create")
    public String createCategory(@RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(CategoryDTO.of(request));
    }

    @PutMapping("/update")
    public String updateCategory(@RequestBody CategoryDTO categoryDTO){
        return categoryService.updateCategory(categoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable long id){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setActive(false);
        categoryService.deleteCategory(categoryDTO);
    }

    @GetMapping("/get")
    public CategoryDTO getCategory(@RequestParam long id){

        return categoryService.getCategory(id);
    }

    @GetMapping("/getAll")
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories().stream()
                .filter(CategoryDTO::isActive)
                .collect(Collectors.toList());
    }

}
