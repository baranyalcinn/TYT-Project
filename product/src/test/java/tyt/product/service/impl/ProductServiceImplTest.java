package tyt.product.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tyt.product.repository.CategoryRepository;
import tyt.product.repository.ProductRepository;
import tyt.product.exception.Exceptions;
import tyt.product.model.CategoryEntity;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the ProductServiceImpl class.
 */
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository; // Mocked ProductRepository

    @Mock
    private CategoryRepository categoryRepository; // Mocked CategoryRepository

    @InjectMocks
    private ProductServiceImpl productService; // Service to be tested

    private ProductDTO productDTO; // Test ProductDTO
    private ProductEntity productEntity; // Test ProductEntity

    /**
     * This method sets up the test environment before each test.
     * It initializes the Mockito annotations, creates a test CategoryEntity and ProductEntity,
     * and mocks the behavior of the categoryRepository and productRepository.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize Mockito annotations

        // Create a CategoryEntity for testing
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Test Category");

        // Mock the behavior of categoryRepository.findById
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));

        // Mock the behavior of categoryRepository.save
        Mockito.when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        // Create a list of ProductEntity for testing
        List<ProductEntity> products = new ArrayList<>();
        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("Test Product");
        products.add(product);

        // Mock the behavior of productRepository.findByCategory
        when(productRepository.findByCategory(any(CategoryEntity.class))).thenReturn(products);

        // Initialize productDTO and productEntity
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");

        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Test Product");
    }

    /**
     * Test case for successful product creation.
     * It mocks the behavior of productRepository. Save and tests the createProduct method of the productService.
     * The test asserts that the returned product ID matches the expected ID.
     */
    @Test
    void createProductSuccessfully() {
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        String result = productService.createProduct(productDTO);

        assertEquals(productEntity.getId().toString(), result);
    }

    /**
     * Test case for successful product update.
     * It ensures a ProductEntity with the same ID exists, mocks the behavior of productRepository. Save,
     * and tests the updateProduct method of the productService.
     * The test asserts that no exception is thrown during the update process.
     */
    @Test
    void updateProductSuccessfully() {
        ProductDTO testProductDTO = new ProductDTO();
        testProductDTO.setId(1L);

        ProductEntity testProductEntity = new ProductEntity();
        testProductEntity.setId(1L);
        testProductEntity.setName("Test Product");

        // Ensure a ProductEntity with the same ID exists
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(testProductEntity));
        // Mock the save method to return the testProductEntity
        when(productRepository.save(any(ProductEntity.class))).thenReturn(testProductEntity);
        // Then call the method
        assertDoesNotThrow(() -> productService.updateProduct(testProductDTO));
    }

    /**
     * Test case for product update when product is not found.
     * It mocks the behavior of productRepository.findById to return an empty Optional,
     * and tests the updateProduct method of the productService.
     * The test asserts that a NoSuchProductException is thrown.
     */
    @Test
    void updateProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exceptions.NoSuchProductException.class, () -> productService.updateProduct(productDTO));
    }

    /**
     * Test case for successful product deletion.
     * It mocks the behavior of productRepository.findById to return a ProductEntity,
     * and tests the deleteProduct method of the productService.
     * The test verifies that the save method of the productRepository is called once.
     */
    @Test
    void deleteProductSuccessfully() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));

        productService.deleteProduct(productDTO);

        verify(productRepository, times(1)).save(productEntity);
    }

    /**
     * Test case for product deletion when product is not found.
     * It mocks the behavior of productRepository.findById to return an empty Optional,
     * and tests the deleteProduct method of the productService.
     * The test asserts that a NoSuchProductException is thrown.
     */
    @Test
    void deleteProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exceptions.NoSuchProductException.class, () -> productService.deleteProduct(productDTO));
    }

    /**
     * Test case for successful product retrieval.
     * It mocks the behavior of productRepository.findById to return a ProductEntity,
     * and tests the getProductById method of the productService.
     * The test asserts that the returned product ID matches the expected ID.
     */
    @Test
    void getProductByIdSuccessfully() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));

        ProductDTO result = productService.getProductById(1L);

        assertEquals(productEntity.getId(), result.getId());
    }

    /**
     * Test case for product retrieval when product is not found.
     * It mocks the behavior of productRepository.findById to return an empty Optional,
     * and tests the getProductById method of the productService.
     * The test asserts that a NoSuchProductException is thrown.
     */
    @Test
    void getProductByIdNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exceptions.NoSuchProductException.class, () -> productService.getProductById(1L));
    }

    /**
     * Test case for successful retrieval of all products.
     * It mocks the behavior of productRepository.findAll to return a list of ProductEntity,
     * and tests the getAllProducts method of the productService.
     * The test asserts that the returned list is not empty and the ID of the first product matches the expected ID.
     */
    @Test
    void getAllProductsSuccessfully() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(productEntity));

        List<ProductDTO> result = productService.getAllProducts();

        assertFalse(result.isEmpty());
        assertEquals(productEntity.getId(), result.get(0).getId());
    }

    /**
     * Test case for successful retrieval of all products by category.
     * It creates a CategoryEntity and tests the getProductsByCategoryById method of the productService.
     * The test asserts that the category is not null.
     */
    @Test
    void getProductsByCategoryById() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1L);
        category.setName("Test Category");

        // Ensure category is not null
        assertNotNull(category);

        // Then call the method
        productService.getProductsByCategory(category);
    }
}