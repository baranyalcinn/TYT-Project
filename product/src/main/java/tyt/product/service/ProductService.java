package tyt.product.service;

import org.springframework.data.domain.Page;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    String createProduct(ProductDTO productDTO);

    String updateProduct(ProductDTO productDTO) throws IllegalArgumentException;

    String deleteProduct(ProductDTO productDTO);

    ProductDTO getProductById(long id);

    List<ProductDTO> getAllProducts();

//    Page<ProductDTO> getProducts(int page, int size, String sortBy, String sortDirection, String name, Double minPrice, Double maxPrice);

    List<ProductDTO> getProductsByCategory(CategoryEntity category);
}