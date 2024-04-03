package tyt.sales.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyt.sales.controller.request.CartRequest;
import tyt.sales.model.CartEntity;
import tyt.sales.model.ProductEntity;
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
public ResponseEntity<Void> addToCart(@RequestBody CartRequest cartRequest) {
    ProductEntity product = productService.findById(cartRequest.getProductId());
    if (product != null) {
        cartService.addToCart(product, cartRequest.getQuantity());
        return new ResponseEntity<>(HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    @DeleteMapping("/remove")
    public void removeItemFromCart(@RequestParam Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
    }

    @GetMapping("/getCart")
    public CartEntity getCart() {
        return cartService.getCart();
    }
}
