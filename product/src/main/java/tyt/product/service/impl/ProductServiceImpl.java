package tyt.product.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tyt.product.database.ProductRepository;
import tyt.product.exception.NoSuchCategoryException;
import tyt.product.exception.NoSuchProductException;
import tyt.product.exception.ProductExistException;
import tyt.product.model.CategoryEntity;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;
import tyt.product.model.mapper.ProductMapper;
import tyt.product.service.ProductService;

import java.util.List;

/**
 * Service class for managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    /**
     * Converts a ProductEntity to a ProductDTO.
     *
     * @param productEntity the product entity to convert
     * @return the converted product DTO
     */
    private ProductDTO toDTO(ProductEntity productEntity) {
        return productMapper.toDTO(productEntity);
    }

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
     * Constructor for the ProductServiceImpl.
     *
     * @param productRepository the product repository to use
     */
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
        ProductEntity savedEntity = productRepository.save(toEntity(productDTO));
        log.info("Product created successfully. ID: {}", savedEntity.getId());
        return savedEntity.getId().toString();
        } else {
            log.error("Product with name {} already exists", productDTO.getName());
            throw new ProductExistException("A product with the name " + productDTO.getName() + " already exists.");
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
                -> new NoSuchProductException("Product with id " + productDTO.getId() + " not found"));

        productMapper.updateProductFromDTO(productDTO, productEntity);
        ProductEntity updatedEntity = productRepository.save(productEntity);

        log.info("Product updated successfully. ID: {}", updatedEntity.getId());
        return "Product updated successfully. ID: " + updatedEntity.getId();
    }

    /**
     * Deletes a product.
     *
     * @param productDTO the product DTO to delete
     */
    @Override
    public void deleteProduct(ProductDTO productDTO) {

        ProductEntity productEntity = productRepository.findById(productDTO.getId()).orElseThrow(() -> new NoSuchProductException("Product with id " + productDTO.getId() + " not found"));

        productEntity.setActive(false);
        log.info("Product deleted successfully. ID: {}", productEntity.getId());
        productRepository.save(productEntity);
    }
    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the retrieved product DTO
     */
@Override
public ProductDTO getProduct(long id) {
    return productRepository.findById(id).map(productMapper::toDTO).orElseThrow(() -> new NoSuchProductException("Product with id " + id + " not found"));
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
                .map(ProductMapper.INSTANCE::toDTO)
                .toList();
    }


    /**
     * Retrieves all products by category.
     *
     * @return a list of product data transfer objects of all products.
     */
    @Override
    public List<ProductDTO> getProductsByCategory(CategoryEntity category) {
        try {
            if (category == null) {
                throw new NoSuchCategoryException("Category cannot be null");
            }

            List<ProductEntity> productEntities = productRepository.findByCategory(category);

            if (productEntities.isEmpty()) {
                throw new NoSuchProductException("No products found for the given category");
            }

            return productEntities.stream().map(ProductMapper.INSTANCE::toDTO).toList();
        } catch (NoSuchCategoryException | NoSuchProductException e) {


            System.err.println(e.getMessage());
            throw e;
        }
    }
}