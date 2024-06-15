package tyt.sales.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tyt.sales.controller.request.CartRequest;
import tyt.sales.controller.response.CartResponse;
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
@Log4j2
public class CartController {

    private final ProductService productService;
    private final CartService cartService;


    /**
     * Endpoint for adding a product to the cart.
     *
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
    public ResponseEntity<CartResponse> checkout() throws CartIsEmptyException, IOException {
        if (cartService.getCart().isEmpty()) {
            throw new CartIsEmptyException("Cart is empty, cannot checkout.");
        }
        log.info("Checking out cart.");
        return ResponseEntity.ok(new CartResponse(cartService.checkout(), HttpStatus.OK.value()));
    }

    @ExceptionHandler(CartIsEmptyException.class)
    public ResponseEntity<CartResponse> handleCartIsEmptyException(CartIsEmptyException ex) {
        log.error("Cart is empty, cannot checkout.");
        return ResponseEntity.badRequest().body(new CartResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException() {
        log.error("An error occurred during checkout.");
        return ResponseEntity.badRequest().body("An error occurred during checkout.");
    }

    /**
     * Endpoint for removing an item from the cart.
     *
     * @param cartItemId The ID of the cart item to remove.
     * @return A response entity containing a message and HTTP status.
     */
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long cartItemId) {
        String result = cartService.removeItemFromCart(cartItemId);

        if (!result.startsWith("Error:")) {
            CartResponse successResponse = new CartResponse(result, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else {
            CartResponse errorResponse = new CartResponse(result.substring(7), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Endpoint for getting the current state of the cart.
     *
     * @return The current state of the cart as a list of CartDTO objects.
     */
    @GetMapping("/getCart")
    public List<CartDTO> getCart() {
        return cartService.getCart();
    }

    /**
     * Endpoint for removing all items from the cart.
     *
     * @return A response entity containing a message and HTTP status.
     */
    @DeleteMapping("/all")
    public ResponseEntity<CartResponse> removeAllItemsFromCart() {
        String result = cartService.removeAllItemsFromCart();

        if (result.startsWith("Error:")) {
            CartResponse errorResponse = new CartResponse(result.substring(7), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        CartResponse successResponse = new CartResponse(result, HttpStatus.OK.value());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    /**
     * Endpoint for applying a campaign to the cart.
     *
     * @return A response entity containing a message and HTTP status.
     */
    @PostMapping("/apply-campaign")
    public ResponseEntity<String> applyCampaign(@RequestBody OfferApplyDTO offerApplyDTO) {
        cartService.applyCampaign(offerApplyDTO.getOfferId());
        return ResponseEntity.ok().body("Campaign applied successfully");
    }
}