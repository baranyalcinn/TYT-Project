package tyt.sales.service;

import tyt.sales.model.dto.ProductDTO;

public interface ProductService {

 Iterable<ProductDTO> getAllProducts();

 ProductDTO getProduct(Long id);

 ProductDTO save (ProductDTO productDTO);

    ProductDTO findById(Long productId);
}
