package tyt.product.controller;

import org.springframework.web.bind.annotation.*;
import tyt.product.controller.request.CreateProductRequest;
import tyt.product.model.dto.ProductDTO;
import tyt.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get/{id}")
    public ProductDTO getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @GetMapping("/getAll")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts().stream()
                .filter(ProductDTO::isActive)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public String createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(ProductDTO.of(request));
    }

    @PutMapping("/update")
    public String updateProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(ProductDTO.of(request));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable long id) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId((long) id);
        productDTO.setActive(false); // Set isActive to false for soft delete
        productService.deleteProduct(productDTO);
    }

}