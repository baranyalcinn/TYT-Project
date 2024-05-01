package tyt.sales.service;

import org.springframework.stereotype.Service;
import tyt.sales.database.ProductRepository;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.ProductDTO;

import java.util.stream.Collectors;

/**
 * Service class for managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Constructor for ProductServiceImpl.
     *
     * @param productRepository the repository to access product data
     */
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Fetches all products from the repository.
     *
     * @return a list of all products as ProductDTO objects
     */
    @Override
    public Iterable<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::toProductDTO).collect(Collectors.toList());
    }

    /**
     * Fetches a product by its ID.
     *
     * @param id the ID of the product to fetch
     * @return the product as a ProductDTO object, or null if not found
     */
    @Override
    public ProductDTO getProduct(Long id) {
        return productRepository.findById(id).map(this::toProductDTO).orElse(null);
    }

    /**
     * Saves a product to the repository.
     *
     * @param productDTO the product to save
     * @return the saved product as a ProductDTO object
     */
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        ProductEntity entity = ProductDTO.toEntity(productDTO);
        ProductEntity savedEntity = productRepository.save(entity);
        return toProductDTO(savedEntity);
    }

    /**
     * Fetches a product by its ID.
     *
     * @param productId the ID of the product to fetch
     * @return the product as a ProductDTO object, or null if not found
     */
    @Override
    public ProductDTO findById(Long productId) {
        return productRepository.findById(productId)
                .map(this::toProductDTO)
                .orElse(null);
    }

    /**
     * Converts a ProductEntity object to a ProductDTO object.
     *
     * @param productEntity the product entity to convert
     * @return the converted product as a ProductDTO object
     */
    private ProductDTO toProductDTO(ProductEntity productEntity) {
        return ProductDTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .build();
    }

}