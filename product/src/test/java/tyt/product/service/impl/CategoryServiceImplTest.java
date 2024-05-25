package tyt.product.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import tyt.product.repository.CategoryRepository;
import tyt.product.exception.Exceptions;
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
class CategoryServiceImplTest {

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
    void setUp() {
        // Create a CategoryEntity for testing
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Test Category");

        // Mock the behavior of categoryRepository.findById
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));

        // Mock the behavior of categoryRepository.save
        Mockito.when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
    }

    /**
     * Test case for category creation with an existing name.
     * It should throw a CategoryExistsException.
     */
    @Test
    void createCategoryWithExistingName() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Electronics");

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Electronics");

        assertNotNull(categoryEntity);

        when(categoryRepository.findByName(anyString())).thenReturn(categoryEntity);
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        assertThrows(Exceptions.CategoryExistsException.class, () -> categoryService.createCategory(categoryDTO));
    }

    /**
     * Test case for updating a non-existing category.
     * It should throw a NoSuchCategoryException.
     */
    @Test
    void updateNonExistingCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Electronics");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exceptions.NoSuchCategoryException.class, () -> categoryService.updateCategory(categoryDTO));
    }

    /**
     * Test case for deleting a non-existing category.
     * It should throw a NoSuchCategoryException.
     */
    @Test
    void deleteNonExistingCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(Exceptions.NoSuchCategoryException.class, () -> categoryService.deleteCategory(categoryDTO));
    }

    /**
     * Test case for getting a non-existing category.
     * It should throw a NoSuchCategoryException.
     */
    @Test
    void getNonExistingCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(Exceptions.NoSuchCategoryException.class, () -> categoryService.getCategory(1L));
    }

    /**
     * Test case for updating a category successfully.
     * It should not throw any exception and return a success message.
     */
    @Test
    void updateCategorySuccessfully() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Electronics");

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Electronics");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        String response = categoryService.updateCategory(categoryDTO);

        assertTrue(response.contains("Category updated successfully"));
    }

    /**
     * Test case for creating a category successfully.
     * It should not throw any exception and return a success message.
     */
    @Test
    void createCategorySuccessfully() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Electronics");

        assertNotNull(categoryEntity);

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Electronics");

        when(categoryRepository.findByName(anyString())).thenReturn(null);

        String response = categoryService.createCategory(categoryDTO);

        assertNotNull(response);

        assertTrue(response.contains("Category created successfully"));
    }

    /**
     * Test case for getting a category successfully.
     * It should not throw any exception.
     */
    @Test
    void getCategorySuccessfully() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Electronics");

        // Ensure a CategoryEntity with the same ID exists
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        // Then call the method
        assertDoesNotThrow(() -> categoryService.getCategory(1L));
    }

    /**
     * Test case for deleting an existing category.
     * It should not throw any exception.
     */
    @Test
    void deleteExistingCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Electronics");

        // Ensure a CategoryEntity with the same ID exists
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryEntity));
        // Then call the method
        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryDTO));
    }
}