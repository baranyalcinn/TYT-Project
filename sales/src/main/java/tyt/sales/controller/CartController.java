package tyt.sales.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyt.sales.controller.request.CartRequest;
import tyt.sales.controller.response.CartResponse;
import tyt.sales.model.CartEntity;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.OfferApplyDTO;
import tyt.sales.rules.CartIsEmptyException;
import tyt.sales.service.CartService;
import tyt.sales.service.ProductService;

import java.io.IOException;
import java.util.List;

/**
 * This is a REST controller for managing the shopping cart.
 * It provides endpoints for adding items to the cart, removing items from the cart, viewing the cart, and checking out.
 */
@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@SessionAttributes("cart")
public class CartController {

    private final ProductService productService;
    private final CartService cartService;

    @ModelAttribute("cart")
    public CartEntity cart(HttpSession session) {
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartEntity();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    /**
     * Endpoint for adding a product to the cart.
     * @param cartRequest The request body containing the product ID and quantity.
     * @return A response entity containing a message and HTTP status.
     */
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody CartRequest cartRequest) {
        var product = productService.findById(cartRequest.getProductId());
        var response = product != null ?
                new CartResponse(cartService.addToCart(product, cartRequest.getQuantity()), HttpStatus.OK.value()) :
                new CartResponse("Product not found", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, product != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint for checking out the cart.
     *
     * @return A response entity containing a message and HTTP status.
     */
    @PostMapping("/checkout")
    public ResponseEntity<CartResponse> checkout() {
        try {
            if (cartService.getCart().isEmpty()) throw new CartIsEmptyException("Cart is empty, cannot checkout.");
            return new ResponseEntity<>(new CartResponse(cartService.checkout(), HttpStatus.OK.value()), HttpStatus.OK);
        } catch (CartIsEmptyException e) {
            return new ResponseEntity<>(new CartResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Endpoint for removing an item from the cart.
     * @param cartItemId The ID of the cart item to remove.
     * @return A response entity containing a message and HTTP status.
     */
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long cartItemId) {
        try {
            String result = cartService.removeItemFromCart(cartItemId);
            CartResponse cartResponse = new CartResponse(result, HttpStatus.OK.value());
            return new ResponseEntity<>(cartResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            CartResponse cartResponse = new CartResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(cartResponse, HttpStatus.BAD_REQUEST);
    }
    }

    /**
     * Endpoint for getting the current state of the cart.
     * @return The current state of the cart as a list of CartDTO objects.
     */
    @GetMapping("/getCart")
    public List<CartDTO> getCart() {
        return cartService.getCart();
    }

    /**
     * Endpoint for removing all items from the cart.
     * @return A response entity containing a message and HTTP status.
     */
    @DeleteMapping("/all")
    public ResponseEntity<CartResponse> removeAllItemsFromCart() {
        try {
            String result = cartService.removeAllItemsFromCart();
            CartResponse cartResponse = new CartResponse(result, HttpStatus.OK.value());
            return new ResponseEntity<>(cartResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            CartResponse cartResponse = new CartResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(cartResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for applying a campaign to the cart.
     *
     * @return A response entity containing a message and HTTP status.
     */
    @PostMapping("/apply-campaign")
    public ResponseEntity<String> applyCampaign(@RequestBody OfferApplyDTO offerApplyDTO) {
        cartService.applyCampaign(offerApplyDTO.getCartId(), offerApplyDTO.getOfferId());
        return ResponseEntity.ok().body("Campaign applied successfully");
    }
}