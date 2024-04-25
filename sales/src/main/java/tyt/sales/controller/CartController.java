package tyt.sales.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyt.sales.controller.request.CartRequest;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.service.CartService;
import tyt.sales.service.ProductService;

import java.util.List;

/**
 * This is a REST controller for managing the shopping cart.
 * It provides endpoints for adding items to the cart, removing items from the cart, viewing the cart, and checking out.
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final CartService cartService;

    /**
     * Constructor for the CartController.
     * @param productService The service for managing products.
     * @param cartService The service for managing the shopping cart.
     */
    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    /**
     * Endpoint for adding a product to the cart.
     * @param cartRequest The request body containing the product ID and quantity.
     * @return A response entity containing a message and HTTP status.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest cartRequest) {
        ProductDTO product = productService.findById(cartRequest.getProductId());
        if (product != null) {
            String result = cartService.addToCart(product, cartRequest.getQuantity());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for removing an item from the cart.
     * @param cartItemId The ID of the cart item to remove.
     */
    @DeleteMapping("/remove{cartItemId}")
    public void removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
    }

    /**
     * Endpoint for getting the current state of the cart.
     * @return The current state of the cart.
     */
    @GetMapping("/getCart")
    public List<CartDTO> getCart() {
        return cartService.getCart();
    }

    /**
     * Endpoint for checking out the cart.
     * @return A response entity containing a message and HTTP status.
     */
   @PostMapping("/checkout")
public ResponseEntity<String> checkout() {
    try {
        String result = cartService.checkout();
        return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

}