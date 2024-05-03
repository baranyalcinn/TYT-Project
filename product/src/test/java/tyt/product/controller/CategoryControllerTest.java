package tyt.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tyt.product.model.dto.CategoryDTO;
import tyt.product.service.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void createCategoryReturnsSuccess() throws Exception {
        when(categoryService.createCategory(any())).thenReturn("Category created successfully");
        mockMvc.perform(post("/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Category\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCategoryReturnsSuccess() throws Exception {
        when(categoryService.updateCategory(any())).thenReturn("Category updated successfully");
        mockMvc.perform(put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Category\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCategoryReturnsSuccess() throws Exception {
        mockMvc.perform(delete("/category/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategoryReturnsSuccess() throws Exception {
        when(categoryService.getCategory(any())).thenReturn(new CategoryDTO());
        mockMvc.perform(get("/category/get?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCategoriesReturnsSuccess() throws Exception {
        List<CategoryDTO> categories = Arrays.asList(new CategoryDTO(), new CategoryDTO());
        when(categoryService.getAllCategories()).thenReturn(categories);
        mockMvc.perform(get("/category/all"))
                .andExpect(status().isOk());
    }
}