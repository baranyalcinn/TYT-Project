package tyt.product.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tyt.product.database.CategoryRepository;
import tyt.product.exception.CategoryExistsException;
import tyt.product.exception.NoSuchCategoryException;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.CategoryDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the CategoryServiceImpl class.
 * It uses the Spring Boot Test framework and Mockito for mocking dependencies.
 */
@SpringBootTest
public class CategoryServiceImplTest {

    // Mocked CategoryRepository instance
    @Mock
    private CategoryRepository categoryRepository;

    // CategoryServiceImpl instance with injected mocks
    @InjectMocks
    private CategoryServiceImpl categoryService;

    /**
     * This method is executed before each test.
     * It initializes the mocks.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for successful category creation.
     */
    @Test
    public void createCategorySuccessfully() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Electronics");

        when(categoryRepository.findByName(anyString())).thenReturn(null);

        String response = categoryService.createCategory(categoryDTO);

        assertTrue(response.contains("Category created successfully"));
    }

    /**
     * Test case for category creation with an existing name.
     */
    @Test
    public void createCategoryWithExistingName() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Electronics");

        when(categoryRepository.findByName(anyString())).thenReturn(new CategoryEntity());

        assertThrows(CategoryExistsException.class, () -> categoryService.createCategory(categoryDTO));
    }

    /**
     * Test case for successful category update.
     */
    @Test
    public void updateCategorySuccessfully() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Electronics");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new CategoryEntity()));

        String response = categoryService.updateCategory(categoryDTO);

        assertTrue(response.contains("Category updated successfully"));
    }

    /**
     * Test case for updating a non-existing category.
     */
    @Test
    public void updateNonExistingCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Electronics");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchCategoryException.class, () -> categoryService.updateCategory(categoryDTO));
    }

    /**
     * Test case for deleting an existing category.
     */
    @Test
    public void deleteExistingCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new CategoryEntity()));

        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryDTO));
    }

    /**
     * Test case for deleting a non-existing category.
     */
    @Test
    public void deleteNonExistingCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchCategoryException.class, () -> categoryService.deleteCategory(categoryDTO));
    }

    /**
     * Test case for getting an existing category.
     */
    @Test
    public void getCategorySuccessfully() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new CategoryEntity()));

        assertDoesNotThrow(() -> categoryService.getCategory(1L));
    }

    /**
     * Test case for getting a non-existing category.
     */
    @Test
    public void getNonExistingCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchCategoryException.class, () -> categoryService.getCategory(1L));
    }
}