package tyt.product.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.product.database.ProductRepository;
import tyt.product.exception.NoSuchProductException;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;

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

    @InjectMocks
    private ProductServiceImpl productService; // Service to be tested

    private ProductDTO productDTO; // Test ProductDTO
    private ProductEntity productEntity; // Test ProductEntity

    /**
     * This method sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productDTO = new ProductDTO();
        productDTO.setId(1L);

        productEntity = new ProductEntity();
        productEntity.setId(1L);
    }

    /**
     * Test case for successful product creation.
     */
    @Test
    void createProductSuccessfully() {
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        String result = productService.createProduct(productDTO);

        assertEquals(productEntity.getId().toString(), result);
    }

    /**
     * Test case for successful product update.
     */
    @Test
    void updateProductSuccessfully() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        String result = productService.updateProduct(productDTO);

        assertTrue(result.contains(productEntity.getId().toString()));
    }

    /**
     * Test case for product update when product is not found.
     */
    @Test
    void updateProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> productService.updateProduct(productDTO));
    }

    /**
     * Test case for successful product deletion.
     */
    @Test
    void deleteProductSuccessfully() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));

        productService.deleteProduct(productDTO);

        verify(productRepository, times(1)).save(productEntity);
    }

    /**
     * Test case for product deletion when product is not found.
     */
    @Test
    void deleteProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> productService.deleteProduct(productDTO));
    }

    /**
     * Test case for successful product retrieval.
     */
    @Test
    void getProductSuccessfully() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));

        ProductDTO result = productService.getProduct(1L);

        assertEquals(productEntity.getId(), result.getId());
    }

    /**
     * Test case for product retrieval when product is not found.
     */
    @Test
    void getProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> productService.getProduct(1L));
    }

    /**
     * Test case for successful retrieval of all products.
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
     */

    @Test
    void getProductsByCategory() {
        when(productRepository.findByCategory(any())).thenReturn(Collections.singletonList(productEntity));

        List<ProductDTO> result = productService.getProductsByCategory(null);

        assertFalse(result.isEmpty());
        assertEquals(productEntity.getId(), result.get(0).getId());
    }
}