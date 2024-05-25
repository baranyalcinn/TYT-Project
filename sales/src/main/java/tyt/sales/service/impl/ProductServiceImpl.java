package tyt.sales.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.model.mapper.ProductMapper;
import tyt.sales.repository.ProductRepository;
import tyt.sales.service.ProductService;

/**
 * Service class for managing products.
 */
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;

    /**
     * Fetches all products from the repository, maps them to DTOs and returns them.
     *
     * @return a list of all product DTOs
     */
    @Override
    public Iterable<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::fromEntity).toList();
    }

    /**
     * Fetches a product by its ID, maps it to a DTO and returns it.
     * If the product does not exist, returns null.
     *
     * @param id the ID of the product to fetch
     * @return the product DTO or null if the product does not exist
     */
    @Override
    public ProductDTO getProduct(Long id) {
        return productRepository.findById(id).map(productMapper::fromEntity).orElse(null);
    }

    /**
     * Saves a product to the repository.
     * The input product is first mapped to an entity, then saved to the repository, then mapped back to a DTO and returned.
     *
     * @param productDTO the product DTO to save
     * @return the saved product DTO
     */
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        ProductEntity entity = productMapper.toEntity(productDTO);
        ProductEntity savedEntity = productRepository.save(entity);
        return productMapper.fromEntity(savedEntity);
    }

    /**
     * Fetches a product by its ID, maps it to a DTO and returns it.
     * If the product does not exist, returns null.
     *
     * @param productId the ID of the product to fetch
     * @return the product DTO or null if the product does not exist
     */
    @Override
    public ProductDTO findById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::fromEntity)
                .orElse(null);
    }
}