package tyt.sales.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import tyt.sales.model.*;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.repository.*;
import tyt.sales.rules.InsufficientStockException;
import tyt.sales.rules.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @Mock
    private WebClient webClient;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToCart_productNotFound_throwsException() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.addToCart(productDTO, 1));
    }

    @Test
    void addToCart_insufficientStock_throwsException() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setStock(0);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productEntity));

        assertThrows(InsufficientStockException.class, () -> cartService.addToCart(productDTO, 1));
    }

    @Test
    void addToCart_productAddedToCart_returnsSuccessMessage() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setStock(10);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(productEntity);
        cartEntity.setQuantity(0);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productEntity));
        when(cartRepository.findByProductId(any(Long.class))).thenReturn(Optional.of(cartEntity));

        String result = cartService.addToCart(productDTO, 1);

        assertEquals("Product added to cart: Test Product", result);
    }

    @Test
    void removeAllItemsFromCart_clearsCart_returnsSuccessMessage() {
        String result = cartService.removeAllItemsFromCart();
        assertEquals("All items removed from cart", result);
        verify(cartRepository, times(1)).deleteAll();
    }

    @Test
    void removeItemFromCart_removesItem_returnsSuccessMessage() {
        Long productId = 1L;
        doNothing().when(cartRepository).deleteCartItemEntityByProductId(productId);
        String result = cartService.removeItemFromCart(productId);
        assertEquals("Product removed from cart", result);
        verify(cartRepository, times(1)).deleteCartItemEntityByProductId(productId);
    }

    @Test
    void checkout_cartIsEmpty_returnsErrorMessage() {
        when(cartRepository.findAll()).thenReturn(new ArrayList<>());
        String result = cartService.checkout();
        assertEquals("Cart is empty!", result);
    }

    @Test
    void getCart_returnsCartItems() {
        List<CartEntity> cartItems = new ArrayList<>();
        CartEntity cartItem = new CartEntity();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Test Product");
        productEntity.setPrice(100.0);
        cartItem.setProduct(productEntity);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);

        when(cartRepository.findAll()).thenReturn(cartItems);
        when(productRepository.findAllById(any())).thenReturn(List.of(productEntity));

        List<CartDTO> result = cartService.getCart();

        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
        assertEquals(100.0, result.get(0).getProductPrice());
        assertEquals(1, result.get(0).getQuantity());
    }

}