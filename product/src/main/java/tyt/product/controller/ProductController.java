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
import tyt.product.controller.response.ProductResponse;
import tyt.product.model.CategoryEntity;
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
    private static final int PAGE_SIZE = 5;

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
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        log.info("Creating product with name: {}", request.getName());
        String message = productService.createProduct(productMapper.createRequestToDto(request));
        ProductResponse productResponse = new ProductResponse(message, HttpStatus.CREATED.value());
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    /**
     * Update an existing product.
     *
     * @param request The request body containing the updated details of the product.
     * @return A message indicating the result of the operation.
     */
    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@Valid @RequestBody UpdateProductRequest request) {
        String message = productService.updateProduct(productMapper.updateRequestToDto(request));
        ProductResponse productResponse = new ProductResponse(message, HttpStatus.OK.value());
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    /**
     * Soft delete a product by setting its isActive field to false.
     *
     * @param id The id of the product to be deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@Valid @PathVariable long id) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        String message = productService.deleteProduct(productDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
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