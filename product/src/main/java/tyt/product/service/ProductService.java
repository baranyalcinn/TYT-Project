package tyt.product.service;

import tyt.product.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    String createProduct(ProductDTO productDTO);

    String updateProduct(ProductDTO productDTO) throws Exception;

    void deleteProduct(ProductDTO productDTO);

    ProductDTO getProduct(long id);

    List<ProductDTO> getAllProducts();
}
