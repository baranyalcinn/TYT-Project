package tyt.sales.service;

import tyt.sales.model.dto.ProductDTO;

/**
 * ProductService is an interface that defines the contract for product-related operations.
 * Implementations of this interface should provide the functionality for managing products.
 */
public interface ProductService {

    /**
     * Fetches all products.
     *
     * @return an Iterable of ProductDTO objects representing all products.
     */
    Iterable<ProductDTO> getAllProducts();

    /**
     * Fetches a single product by its ID.
     *
     * @param id the ID of the product to fetch.
     * @return a ProductDTO object representing the product with the given ID.
     */
    ProductDTO getProduct(Long id);

    /**
     * Saves a product.
     *
     * @param productDTO the product to save, represented as a ProductDTO object.
     * @return a ProductDTO object representing the saved product.
     */
    ProductDTO save(ProductDTO productDTO);

    /**
     * Finds a product by its ID.
     *
     * @param productId the ID of the product to find.
     * @return a ProductDTO object representing the product with the given ID.
     */
    ProductDTO findById(Long productId);
}