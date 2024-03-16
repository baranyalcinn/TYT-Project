package tyt.product.controller;

import org.springframework.web.bind.annotation.*;
import tyt.product.controller.request.CreateProductRequest;
import tyt.product.controller.request.UpdateProductRequest;
import tyt.product.model.dto.ProductDTO;
import tyt.product.model.mapper.ProductMapper;
import tyt.product.service.ProductService;

import java.util.List;

/**
 * This is the main controller for the Product entity.
 * It handles all the HTTP requests related to the Product entity.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor for the ProductController.
     *
     * @param productService The service that will be used to handle business logic.
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get a specific product by its id.
     *
     * @param id The id of the product to retrieve.
     * @return The product with the given id.
     */
    @GetMapping("/get/{id}")
    public ProductDTO getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    /**
     * Get all active products.
     *
     * @return A list of all active products.
     */
    @GetMapping("/getAll")
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
    public String createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(ProductMapper.INSTANCE.createRequestToDto(request));
    }

    /**
     * Update an existing product.
     *
     * @param request The request body containing the updated details of the product.
     * @return A message indicating the result of the operation.
     */
    @PutMapping("/update")
    public String updateProduct(@RequestBody UpdateProductRequest request) {
        return productService.updateProduct(ProductMapper.INSTANCE.updateRequestToDto(request));
    }

    /**
     * Soft delete a product by setting its isActive field to false.
     *
     * @param id The id of the product to be deleted.
     */
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable long id) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setActive(false); // Set isActive to false for soft delete
        productService.deleteProduct(productDTO);
    }

}