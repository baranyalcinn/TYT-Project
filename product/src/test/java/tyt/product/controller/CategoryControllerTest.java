package tyt.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tyt.product.database.CategoryRepository;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.CategoryDTO;
import tyt.product.service.CategoryService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains unit tests for the CategoryController class.
 */
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private MockMvc mockMvc;

    /**
     * This method sets up the necessary mocks and objects before each test.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);
        category.setName("Test Category");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
    }

    /**
     * This test checks if the create category endpoint returns a success status.
     */
    @Test
    public void createCategoryReturnsSuccess() throws Exception {
        when(categoryService.createCategory(any())).thenReturn("Category created successfully");
        mockMvc.perform(post("/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Category\"}"))
                .andExpect(status().isOk());
    }

    /**
     * This test checks if the update category endpoint returns a success status.
     */
    @Test
    public void updateCategoryReturnsSuccess() throws Exception {
        when(categoryService.updateCategory(any())).thenReturn("Category updated successfully");
        mockMvc.perform(put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Category\"}"))
                .andExpect(status().isOk());
    }

    /**
     * This test checks if the delete category endpoint returns a success status.
     */
    @Test
    public void deleteCategoryReturnsSuccess() throws Exception {
        mockMvc.perform(delete("/category/1"))
                .andExpect(status().isOk());
    }

    /**
     * This test checks if the get category endpoint returns a success status.
     */
    @Test
    public void getCategoryReturnsSuccess() throws Exception {
        when(categoryService.getCategory(anyLong())).thenReturn(new CategoryDTO());
        mockMvc.perform(get("/category/get?id=1"))
                .andExpect(status().isOk());
    }

    /**
     * This test checks if the get all categories endpoint returns a success status.
     */
    @Test
    public void getAllCategoriesReturnsSuccess() throws Exception {
        List<CategoryDTO> categories = Arrays.asList(new CategoryDTO(), new CategoryDTO());
        when(categoryService.getAllCategories()).thenReturn(categories);
        mockMvc.perform(get("/category/all"))
                .andExpect(status().isOk());
    }
}