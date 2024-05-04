package tyt.product.service;

import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

/**
 * ProductService is an interface that defines the contract for product-related operations.
 * Implementations of this interface should provide the functionality for creating, updating, deleting,
 * retrieving a single product and retrieving all products.
 */
public interface ProductService {

    /**
     * Creates a new product.
     *
     * @param productDTO the product data transfer object containing the details of the product to be created.
     * @return a string message indicating the result of the operation.
     */
    String createProduct(ProductDTO productDTO);

    /**
     * Updates an existing product.
     *
     * @param productDTO the product data transfer object containing the updated details of the product.
     * @return a string message indicating the result of the operation.
     * @throws Exception if an error occurs during the update operation.
     */
    String updateProduct(ProductDTO productDTO) throws Exception;

    /**
     * Deletes a product.
     *
     * @param productDTO the product data transfer object containing the details of the product to be deleted.
     * @return
     */
    String deleteProduct(ProductDTO productDTO);

    /**
     * Retrieves a single product by its ID.
     *
     * @param id the ID of the product to be retrieved.
     * @return the product data transfer object of the retrieved product.
     */
    ProductDTO getProductById(long id);

    /**
     * Retrieves all products.
     *
     * @return a list of product data transfer objects of all products.
     */
    List<ProductDTO> getAllProducts();

//    Optional<ProductDTO> getProductById();

    /**
     * Retrieves all products by category.
     *
     * @return a list of product data transfer objects of all products.
     */
    List<ProductDTO> getProductsByCategory(CategoryEntity category);
}