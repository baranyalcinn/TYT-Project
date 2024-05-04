package tyt.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.product.controller.request.CreateProductRequest;
import tyt.product.controller.request.UpdateProductRequest;
import tyt.product.exception.Exceptions;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.ProductDTO;
import tyt.product.service.ProductService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the ProductController class.
 * It uses Mockito to mock the ProductService class and inject it into the ProductController instance.
 */
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    /**
     * This method is executed before each test.
     * It initializes the mocks.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This test checks if the getProductById method of the ProductController class returns the correct product.
     */
    @Test
    public void getProductsByCategoryReturnsProducts() {
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
    Long nonExistentProductId = 999L; // ID that does not exist in the database

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
    public void getAllProductsReturnsProductList() {
        List<ProductDTO> products = Collections.singletonList(new ProductDTO());
        when(productService.getAllProducts()).thenReturn(products);

        List<ProductDTO> response = productController.getAllProducts();

        assertEquals(products, response);
    }

    /**
     * This test checks if the createProduct method of the ProductController class returns the correct message after creating a product.
     */
    @Test
    public void createProductReturnsMessage() {
        when(productService.createProduct(any())).thenReturn("Product created");

        CreateProductRequest createProductRequest = new CreateProductRequest("name", "description", 1.0, 1, 1L, "category");
        String response = productController.createProduct(createProductRequest);

        assertEquals("Product created", response);
    }

    /**
     * This test checks if the updateProduct method of the ProductController class returns the correct message after updating a product.
     */
    @Test
    public void updateProductReturnsMessage() throws Exception {
        when(productService.updateProduct(any())).thenReturn("Product updated");

        UpdateProductRequest updateProductRequest = new UpdateProductRequest(1L, "name", "description", 1.0, 1, 1L);
        String response = productController.updateProduct(updateProductRequest);

        assertEquals("Product updated", response);
    }

    /**
     * This test checks if the deleteProduct method of the ProductController class calls the deleteProduct method of the ProductService class.
     */
   @Test
public void deleteProductCallsService() {
    when(productService.deleteProduct(any())).thenReturn(null);

    productController.deleteProduct(1L);

    verify(productService, times(1)).deleteProduct(any());
}
}