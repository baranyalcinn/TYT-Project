package tyt.product.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tyt.product.exception.Exceptions;
import tyt.product.model.CategoryEntity;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;
import tyt.product.model.mapper.ProductMapper;
import tyt.product.repository.ProductRepository;
import tyt.product.service.ProductService;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing products.
 */
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;


    /**
     * Converts a ProductDTO to a ProductEntity.
     *
     * @param productDTO the product DTO to convert
     * @return the converted product entity
     */
    private ProductEntity toEntity(ProductDTO productDTO) {
        return productMapper.toEntity(productDTO);
    }


    /**
     * Creates a new product.
     *
     * @param productDTO the product DTO to create
     * @return the ID of the created product
     */
    @Override
    public String createProduct(ProductDTO productDTO) {
        List<ProductEntity> existingProducts = productRepository.findByName(productDTO.getName());
        if (existingProducts.isEmpty()) {
            ProductEntity productEntity = toEntity(productDTO);
            productEntity.setActive(true); // Set active to true here
            ProductEntity savedEntity = productRepository.save(productEntity);
            log.info("Product created successfully. ID: {}", savedEntity.getId());
            return savedEntity.getId().toString();
        } else {
            log.error("Product with name {} already exists", productDTO.getName());
            throw new Exceptions.ProductExistException("A product with the name " + productDTO.getName() + " already exists.");
        }
    }

    /**
     * Updates an existing product.
     *
     * @param productDTO the product DTO to update
     * @return a success message with the ID of the updated product
     */
    @Override
    public String updateProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.findById(productDTO.getId()).orElseThrow(()
                -> new Exceptions.NoSuchProductException("Product with id " + productDTO.getId() + " not found"));

        productMapper.updateProductFromDTO(productDTO, productEntity);
        ProductEntity updatedEntity = productRepository.save(productEntity);

        log.info("Product updated successfully. ID: {}", updatedEntity.getId());
        return "Product updated successfully. ID: " + updatedEntity.getId();
    }

    /**
     * Deletes a product.
     *
     * @param productDTO the product DTO to delete
     * @return a success message with the ID of the deleted product
     * @throws Exceptions.NoSuchProductException if the product with the given ID does not exist
     */
    @Override
    public String deleteProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.findById(productDTO.getId()).orElseThrow(() -> new Exceptions.NoSuchProductException("Product with id " + productDTO.getId() + " not found"));
        productEntity.setActive(false);
        log.info("Product deactivated successfully. ID: {}", productEntity.getId());
        productRepository.save(productEntity);
        return "Product with ID: " + productEntity.getId() + " was successfully deactivated.";
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the retrieved product DTO
     */
    @Override
    public ProductDTO getProductById(long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);

        if (productEntity.isPresent()) {
            return productMapper.toDTO(productEntity.get());
        } else {
            throw new Exceptions.NoSuchProductException("Product with id " + id + " not found");
        }
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all product DTOs
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public Page<ProductDTO> getProducts(Pageable pageable) {
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        return productEntities.map(productMapper::toDTO);
    }

    /**
     * Retrieves all products by category.
     *
     * @return a list of product data transfer objects of all products.
     */
    @Override
    public List<ProductDTO> getProductsByCategory(@NotNull CategoryEntity category) {
        List<ProductEntity> productEntities = productRepository.findByCategory(category);

        if (productEntities.isEmpty()) {
            throw new Exceptions.NoSuchProductException("No products found for the given category");
        }

        return productEntities.stream().map(productMapper::toDTO).toList();
    }


}