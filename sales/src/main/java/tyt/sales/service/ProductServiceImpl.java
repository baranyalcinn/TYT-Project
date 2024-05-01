package tyt.sales.service;

import org.springframework.stereotype.Service;
import tyt.sales.database.ProductRepository;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.model.mapper.ProductMapper;

import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper.INSTANCE::fromEntity).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Long id) {
        return productRepository.findById(id).map(ProductMapper.INSTANCE::fromEntity).orElse(null);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        ProductEntity entity = ProductMapper.INSTANCE.toEntity(productDTO);
        ProductEntity savedEntity = productRepository.save(entity);
        return ProductMapper.INSTANCE.fromEntity(savedEntity);
    }

    @Override
    public ProductDTO findById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductMapper.INSTANCE::fromEntity)
                .orElse(null);
    }
}