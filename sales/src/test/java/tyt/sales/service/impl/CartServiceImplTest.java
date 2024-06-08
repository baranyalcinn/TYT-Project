package tyt.sales.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tyt.sales.model.*;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.OrderDTO;
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
        when(webClient.post()).thenReturn(Mockito.mock(WebClient.RequestBodyUriSpec.class));
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

    @Test
    void applyCampaign_cartNotFound_throwsException() {
        Long cartId = 1L;
        Long campaignId = 1L;

        when(cartRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.applyCampaign(cartId, campaignId));
    }

    @Test
    void applyCampaign_campaignNotFound_throwsException() {
        Long cartId = 1L;
        Long campaignId = 1L;

        CartEntity cartEntity = new CartEntity();

        when(cartRepository.findById(any(Long.class))).thenReturn(Optional.of(cartEntity));
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.applyCampaign(cartId, campaignId));
    }

    @Test
    void applyCampaign_campaignAppliedSuccessfully() {
        Long cartId = 1L;
        Long campaignId = 1L;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setPrice(100.0);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(productEntity);

        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setOfferType(OfferType.TEN_PERCENT_DISCOUNT);

        when(cartRepository.findById(any(Long.class))).thenReturn(Optional.of(cartEntity));
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offerEntity));

        cartService.applyCampaign(cartId, campaignId);

        assertEquals(offerEntity, cartEntity.getAppliedOffer());
    }

    @Test
    void createOrder_cartIsEmpty_returnsEmptyOrder() {
        when(cartRepository.findAll()).thenReturn(new ArrayList<>());

        OrderDTO orderDTO = new OrderDTO();
        cartService.createOrder(orderDTO);

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void createOrder_cartHasItems_returnsOrderWithItems() {
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

        OrderDTO orderDTO = new OrderDTO();
        cartService.createOrder(orderDTO);

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }


    @Test
    void checkout_cartHasItems_returnsSuccessMessage() {
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

        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Order created"));

        when(orderRepository.save(any(OrderEntity.class))).thenAnswer((Answer<OrderEntity>) invocation -> {
            OrderEntity orderEntity = (OrderEntity) invocation.getArguments()[0];
            orderEntity.setId(1L);
            return orderEntity;
        });

        String result = cartService.checkout();

        assertEquals("Checkout successful. Order ID: 1", result);
    }
}
