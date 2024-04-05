package tyt.sales.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyt.sales.controller.request.CartRequest;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.service.CartService;
import tyt.sales.service.ProductService;

@RestController
@RequestMapping("/cart")

public class CartController {


    private final ProductService productService;
    private final CartService cartService;

    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

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

    @DeleteMapping("/remove")
    public void removeItemFromCart(@RequestParam Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
    }

    @GetMapping("/getCart")
    public CartDTO getCart() {
        return cartService.getCart();
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout() {
        try {
            cartService.checkout();
            return new ResponseEntity<>("Checkout successful", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
