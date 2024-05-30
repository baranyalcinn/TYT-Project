package tyt.product.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyt.product.controller.request.CreateProductRequest;
import tyt.product.controller.request.UpdateProductRequest;
import tyt.product.model.CategoryEntity;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;
import tyt.product.model.mapper.ProductMapper;
import tyt.product.service.ProductService;

import java.util.List;

/**
 * This is the main controller for the Product entity.
 * It handles all the HTTP requests related to the Product entity.
 */
@Log4j2
@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;
    private static final int PAGE_SIZE = 10;
    /**
     * Get a specific product by its id.
     *
     * @param id The id of the product to retrieve.
     * @return The product with the given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long id) {
        ProductDTO productDTO = productService.getProductById(id);
        if (productDTO != null) {
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Get all active products.
     *
     * @return A list of all active products.
     */
    @GetMapping("/all")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Create a new product.
     *
     * @param request The request body containing the details of the product to be created.
     * @return A message indicating the result of the operation.
     */

    @PostMapping("/create")
    public String createProduct(@Valid @RequestBody CreateProductRequest request) {

        log.info("Creating product with name: {}", request.getName());
        return productService.createProduct(productMapper.createRequestToDto(request));
    }

    /**
     * Update an existing product.
     *
     * @param request The request body containing the updated details of the product.
     * @return A message indicating the result of the operation.
     */
    @PutMapping("/update")
    public String updateProduct(@Valid @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(productMapper.updateRequestToDto(request));
    }

    /**
     * Soft delete a product by setting its isActive field to false.
     *
     * @param id The id of the product to be deleted.
     */
    @DeleteMapping("/{id}")
    public String deleteProduct(@Valid @PathVariable long id) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setActive(false); // Set isActive to false for soft delete

        ProductDTO productDTO = productMapper.toDTO(productEntity);
        return productService.deleteProduct(productDTO);
    }


    @GetMapping("/paginated")
    public Page<ProductDTO> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        return productService.getProducts(pageable);
    }


    @GetMapping("/byCategory/{categoryId}")
    public List<ProductDTO> getProductsByCategory(@PathVariable long categoryId) {
        CategoryEntity category = new CategoryEntity();
        category.setId(categoryId);
        return productService.getProductsByCategory(category);
    }

}