package tyt.sales.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tyt.sales.controller.request.CartRequest;
import tyt.sales.controller.request.CheckoutRequest;
import tyt.sales.controller.response.CartResponse;
import tyt.sales.model.PaymentMethod;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.rules.CartIsEmptyException;
import tyt.sales.service.CartService;
import tyt.sales.service.ProductService;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToCart_productExists_addsProductToCart() {
        CartRequest cartRequest = new CartRequest(1L, 2);
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100.0);
        when(productService.findById(cartRequest.getProductId())).thenReturn(product);
        when(cartService.addToCart(product, cartRequest.getQuantity())).thenReturn("Product added to cart");
        ResponseEntity<CartResponse> response = cartController.addToCart(cartRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product added to cart", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void addToCart_productDoesNotExist_returnsNotFound() {
        CartRequest cartRequest = new CartRequest(1L, 2);
        when(productService.findById(cartRequest.getProductId())).thenReturn(null);

        ResponseEntity<CartResponse> response = cartController.addToCart(cartRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void checkout_cartNotEmpty_checksOut() throws IOException {
        when(cartService.getCart()).thenReturn(Collections.singletonList(new CartDTO()));

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD; // Use a constant from the PaymentMethod enum
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setPaymentMethod(paymentMethod);
        when(cartService.checkout(paymentMethod)).thenReturn("Checkout successful");

        ResponseEntity<CartResponse> response = cartController.checkout(checkoutRequest); // Pass the CheckoutRequest object to the checkout method

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Checkout successful", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void checkout_cartIsEmpty_throwsException() {
        when(cartService.getCart()).thenReturn(Collections.emptyList());

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        assertThrows(CartIsEmptyException.class, () -> cartController.checkout(checkoutRequest));
    }

    @Test
    void removeItemFromCart_itemExists_removesItem() {
        when(cartService.removeItemFromCart(1L)).thenReturn("Item removed from cart");

        ResponseEntity<CartResponse> response = cartController.removeItemFromCart(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Item removed from cart", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void removeItemFromCart_itemDoesNotExist_returnsError() {
        when(cartService.removeItemFromCart(1L)).thenReturn("Error: Item not found in cart");

        ResponseEntity<CartResponse> response = cartController.removeItemFromCart(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Item not found in cart", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void removeAllItemsFromCart_itemsExist_removesAllItems() {
        when(cartService.removeAllItemsFromCart()).thenReturn("All items removed from cart");

        ResponseEntity<CartResponse> response = cartController.removeAllItemsFromCart();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All items removed from cart", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void removeAllItemsFromCart_noItemsExist_returnsError() {
        when(cartService.removeAllItemsFromCart()).thenReturn("Error: No items in cart to remove");

        ResponseEntity<CartResponse> response = cartController.removeAllItemsFromCart();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No items in cart to remove", Objects.requireNonNull(response.getBody()).getMessage());
    }
}