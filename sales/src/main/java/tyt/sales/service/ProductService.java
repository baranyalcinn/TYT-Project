package tyt.sales.service;

import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.ProductDTO;

public interface ProductService {

 Iterable<ProductDTO> getAllProducts();

 ProductDTO getProduct(Long id);

 ProductDTO save (ProductDTO productDTO);

    ProductEntity findById(Long productId);
}
