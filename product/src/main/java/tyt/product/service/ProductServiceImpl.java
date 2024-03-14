package tyt.product.service;

import org.springframework.stereotype.Service;
import tyt.product.database.ProductRepository;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String createProduct(ProductDTO productDTO) {
        ProductEntity savedEntity = productRepository.save(toEntity(productDTO));
        return savedEntity.getId().toString();
    }

    @Override
    public String updateProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.findById(productDTO.getId()).orElseThrow();
        productEntity.setName(productDTO.getName());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setStock(productDTO.getStock());
        productEntity.setDescription(productDTO.getDescription());
//        productEntity.setCategoryId(productDTO.getCategoryId());
        productRepository.save(productEntity);
        return productEntity.getId().toString();
    }

    @Override
    public void deleteProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.findById(productDTO.getId()).orElseThrow();
        productEntity.setActive(false);
        productRepository.save(productEntity);
    }

    @Override
    public ProductDTO getProduct(long id) {
        return toDTO(productRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(ProductEntity::isActive)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ProductEntity toEntity(ProductDTO productDTO) {
        return ProductEntity.of(productDTO);
    }

    private ProductDTO toDTO(ProductEntity productEntity) {
        return ProductDTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .isActive(productEntity.isActive())
                .stock(productEntity.getStock())
                .description(productEntity.getDescription())
//                .categoryId(productEntity.getCategoryId())
                .build();
    }

}

