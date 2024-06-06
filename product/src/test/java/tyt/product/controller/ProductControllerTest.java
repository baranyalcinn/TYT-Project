package tyt.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tyt.product.controller.request.CreateProductRequest;
import tyt.product.controller.request.UpdateProductRequest;
import tyt.product.controller.response.ProductResponse;
import tyt.product.exception.Exceptions;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.ProductDTO;
import tyt.product.service.ProductService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the ProductController class.
 * It uses Mockito to mock the ProductService class and inject it into the ProductController instance.
 */
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    /**
     * This method is executed before each test.
     * It initializes the mocks.
     */
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This test checks if the getProductById method of the ProductController class returns the correct product.
     */
    @Test
    void getProductsByCategoryReturnsProducts() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1L);
        category.setName("Test Category");

        List<ProductDTO> products = Arrays.asList(new ProductDTO(), new ProductDTO());
        when(productService.getProductsByCategory(any())).thenReturn(products);

        List<ProductDTO> response = productService.getProductsByCategory(category);

        assertEquals(products, response);
    }

    /**
     * This test checks if the getProductById method of the ProductController class returns a not found response when the product is not found.
     */
    @Test
    void getProductByIdReturnsNotFound() {
        // Arrange
        long nonExistentProductId = 999L; // ID that does not exist in the database

        // Set up ProductService to throw NoSuchProductException
        when(productService.getProductById(nonExistentProductId))
                .thenThrow(new Exceptions.NoSuchProductException("Product not found"));

        // Assert
        Exception exception = assertThrows(Exceptions.NoSuchProductException.class, () -> {
            // Act
            productController.getProductById(nonExistentProductId);
        });

        String expectedMessage = "Product not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * This test checks if the getAllProducts method of the ProductController class returns the correct list of products.
     */
    @Test
    void getAllProductsReturnsProductList() {
        List<ProductDTO> products = Collections.singletonList(new ProductDTO());
        when(productService.getAllProducts()).thenReturn(products);

        List<ProductDTO> response = productController.getAllProducts();

        assertEquals(products, response);
    }

    /**
     * This test checks if the createProduct method of the ProductController class returns the correct message after creating a product.
     */
    @Test
    void createProductReturnsMessage() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest("Product name", "Product description", 100.0, 10, 1L, "Category name");
        when(productService.createProduct(any(ProductDTO.class))).thenReturn("Product created");

        // Act
        ResponseEntity<ProductResponse> response = productController.createProduct(request);

        // Assert
        assertEquals("Product created", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void updateProductReturnsMessage() {
        // Arrange
        UpdateProductRequest request = new UpdateProductRequest(1L, "Updated product name", "Updated product description", 200.0, 20, 2L);
        when(productService.updateProduct(any(ProductDTO.class))).thenReturn("Product updated");

        // Act
        ResponseEntity<ProductResponse> response = productController.updateProduct(request);

        // Assert
        assertEquals("Product updated", Objects.requireNonNull(response.getBody()).getMessage());
    }

    /**
     * This test checks if the deleteProduct method of the ProductController class calls the deleteProduct method of the ProductService class.
     */
    @Test
    void deleteProductCallsService() {
        when(productService.deleteProduct(any())).thenReturn(null);

        productController.deleteProduct(1L);

        verify(productService, times(1)).deleteProduct(any());
    }
}