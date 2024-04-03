package tyt.sales.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tyt.sales.database.ProductRepository;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.ProductDTO;

import java.util.stream.Collectors;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::toProductDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Long id) {
        return productRepository.findById(id).map(this::toProductDTO).orElse(null);
    }

@Override
public ProductDTO save(ProductDTO productDTO) {
    ProductEntity entity = ProductDTO.toEntity(productDTO);
    ProductEntity savedEntity = productRepository.save(entity);
    return toProductDTO(savedEntity);
}

    @Override
    public ProductEntity findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    private ProductDTO toProductDTO(ProductEntity productEntity) {
        return ProductDTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .isActive(productEntity.isActive())
                .build();
    }

}
