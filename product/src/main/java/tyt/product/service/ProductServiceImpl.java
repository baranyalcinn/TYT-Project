package tyt.product.service;

import org.springframework.stereotype.Service;
import tyt.product.database.ProductRepository;
import tyt.product.exception.ProductNotFoundException;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;
import tyt.product.model.mapper.ProductMapper;

import java.util.List;

/**
 * Service class for managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

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
        ProductEntity savedEntity = productRepository.save(toEntity(productDTO));
        return savedEntity.getId().toString();
    }

    /**
     * Updates an existing product.
     *
     * @param productDTO the product DTO to update
     * @return a success message with the ID of the updated product
     */
    @Override
    public String updateProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.findById(productDTO.getId()).orElseThrow();
        productMapper.updateProductFromDTO(productDTO,productEntity);
        return "Product updated successfully" + productRepository.save(productEntity).getId();
    }

    /**
     * Deletes a product.
     *
     * @param productDTO the product DTO to delete
     */
    @Override
    public void deleteProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.findById(productDTO.getId()).orElseThrow();
        productEntity.setActive(false);
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
    return productRepository.findById(id)
            .map(productMapper::toDTO)
            .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
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

}