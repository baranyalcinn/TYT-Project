package tyt.product.service;

import org.springframework.stereotype.Service;
import tyt.product.database.ProductRepository;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;
import tyt.product.model.mapper.ProductMapper;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;


    private ProductDTO toDTO(ProductEntity productEntity) {
        return productMapper.toDTO(productEntity);
    }

    private ProductEntity toEntity(ProductDTO productDTO) {
        return productMapper.toEntity(productDTO);
    }


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
        productMapper.updateProductFromDTO(productDTO,productEntity);
        return "Product updated successfully" + productRepository.save(productEntity).getId();
    }


    @Override
    public void deleteProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.findById(productDTO.getId()).orElseThrow();
        productEntity.setActive(false);
        productRepository.save(productEntity);
    }

    @Override
    public ProductDTO getProduct(long id) {
        return productMapper.toDTO(productRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(ProductMapper.INSTANCE::toDTO)
                .toList();
    }

}

