package tyt.sales.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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
    private WebClient.Builder webClient;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        WebClient webClientMock = mock(WebClient.class);
        requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.build()).thenReturn(webClientMock);
        when(webClientMock.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
    }

    private ProductDTO createProductDTO(String name) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName(name);
        return productDTO;
    }

    private ProductEntity createProductEntity(String name, int stock) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName(name);
        productEntity.setPrice(100.0);
        productEntity.setStock(stock);
        return productEntity;
    }

    private CartEntity createCartEntity(ProductEntity productEntity, int quantity) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(productEntity);
        cartEntity.setQuantity(quantity);
        return cartEntity;
    }

    @Test
    void addToCart_productNotFound_throwsException() {
        ProductDTO productDTO = createProductDTO("Test Product");

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.addToCart(productDTO, 1));
    }

    @Test
    void addToCart_insufficientStock_throwsException() {
        ProductDTO productDTO = createProductDTO("Test Product");

        ProductEntity productEntity = createProductEntity("Test Product", 0);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productEntity));

        assertThrows(InsufficientStockException.class, () -> cartService.addToCart(productDTO, 1));
    }

    @Test
    void addToCart_productAddedToCart_returnsSuccessMessage() {
        ProductDTO productDTO = createProductDTO("Test Product");
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartEntity = createCartEntity(productEntity, 0);

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
        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD; // Use a constant from the PaymentMethod enum
        String result = cartService.checkout(paymentMethod); // Pass it to the checkout method
        assertEquals("Cart is empty!", result);
    }

    @Test
    void getCart_returnsCartItems() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartItem = createCartEntity(productEntity, 1);

        when(cartRepository.findAll()).thenReturn(List.of(cartItem));
        when(productRepository.findAllById(any())).thenReturn(List.of(productEntity));

        List<CartDTO> result = cartService.getCart();

        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
        assertEquals(100.0, result.get(0).getProductPrice());
        assertEquals(1, result.get(0).getQuantity());
    }

    @Test
    void applyCampaign_cartIsEmpty_throwsException() {
        when(cartRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> cartService.applyCampaign(1L));
    }

    @Test
    void applyCampaign_campaignNotFound_throwsException() {
        when(cartRepository.findAll()).thenReturn(List.of(createCartEntity(new ProductEntity(), 1)));
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.applyCampaign(1L));
    }

    @Test
    void applyCampaign_campaignAppliedSuccessfully() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartEntity = createCartEntity(productEntity, 1);
        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setOfferType(OfferType.TEN_PERCENT_DISCOUNT);

        when(cartRepository.findAll()).thenReturn(List.of(cartEntity));
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offerEntity));

        cartService.applyCampaign(1L);

        assertEquals(offerEntity, cartEntity.getAppliedOffer());
    }

    @Test
    void createOrder_cartIsEmpty_returnsEmptyOrder() {
        when(cartRepository.findAll()).thenReturn(new ArrayList<>());

        cartService.createOrder(new OrderDTO());

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void checkout_cartHasItems_returnsSuccessMessage() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartItem = createCartEntity(productEntity, 1);

        when(cartRepository.findAll()).thenReturn(List.of(cartItem));
        when(productRepository.findAllById(any())).thenReturn(List.of(productEntity));
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Order created"));

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD; // Use a constant from the PaymentMethod enum
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer((Answer<OrderEntity>) invocation -> {
            OrderEntity orderEntity = (OrderEntity) invocation.getArguments()[0];
            orderEntity.setId(1L);
            return orderEntity;
        });
        String result = cartService.checkout(paymentMethod); // Pass it to the checkout method

        assertEquals("Checkout successful. Order ID: 1. Record creation response will be logged.", result);
        verify(cartRepository, times(1)).deleteAll();
        verify(requestBodyUriSpec, times(1)).uri("http://record-service/record/create/1");
    }

    @Test
    void getCart_returnsEmptyCart_whenNoItemsInCart() {
        when(cartRepository.findAll()).thenReturn(new ArrayList<>());
        List<CartDTO> result = cartService.getCart();
        assertTrue(result.isEmpty());
    }

    @Test
    void createOrder_createsOrderSuccessfully() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartItem = createCartEntity(productEntity, 1);

        when(cartRepository.findAll()).thenReturn(List.of(cartItem));
        when(productRepository.findAllById(any())).thenReturn(List.of(productEntity));

        cartService.createOrder(new OrderDTO());

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void addToCart_newProductAddedToCart_returnsSuccessMessage() {
        ProductDTO productDTO = createProductDTO("New Product");
        ProductEntity productEntity = createProductEntity("New Product", 10);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productEntity));
        when(cartRepository.findByProductId(any(Long.class))).thenReturn(Optional.empty());

        String result = cartService.addToCart(productDTO, 1);

        assertEquals("Product added to cart: New Product", result);
    }

    @Test
    void applyCampaign_validCampaignApplied_calculatesDiscountCorrectly() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartEntity = createCartEntity(productEntity, 5);
        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setOfferType(OfferType.TEN_PERCENT_DISCOUNT);

        when(cartRepository.findAll()).thenReturn(List.of(cartEntity));
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offerEntity));

        cartService.applyCampaign(1L);

        assertEquals(offerEntity, cartEntity.getAppliedOffer());
        assertEquals(450.0, cartEntity.getTotalPrice());
    }

    @Test
    void checkout_webClientPostReturnsError_returnsErrorMessage() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartItem = createCartEntity(productEntity, 1);

        when(cartRepository.findAll()).thenReturn(List.of(cartItem));
        when(productRepository.findAllById(any())).thenReturn(List.of(productEntity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(new OrderEntity());

        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Error")));

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD; // Use a constant from the PaymentMethod enum
        String result = cartService.checkout(paymentMethod); // Pass it to the checkout method

        assertEquals("Checkout successful. Order ID: null. Record creation response will be logged.", result);
    }

    @Test
    void applyCampaign_buyThreePayTwoCampaignApplied_correctlyCalculatesDiscount() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartEntity = createCartEntity(productEntity, 6);
        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setOfferType(OfferType.BUY_THREE_PAY_TWO);

        when(cartRepository.findAll()).thenReturn(List.of(cartEntity));
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offerEntity));

        cartService.applyCampaign(1L);

        assertEquals(offerEntity, cartEntity.getAppliedOffer());
        assertEquals(400.0, cartEntity.getTotalPrice());
    }

    @Test
    void addToCart_productOutOfStock_throwsException() {
        ProductDTO productDTO = createProductDTO("Test Product");
        ProductEntity productEntity = createProductEntity("Test Product", 0);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productEntity));

        assertThrows(InsufficientStockException.class, () -> cartService.addToCart(productDTO, 5));
    }

    @Test
    void addToCart_newProductAddedToCart_quantityIsUpdated() {
        ProductDTO productDTO = createProductDTO("New Product");
        ProductEntity productEntity = createProductEntity("New Product", 10);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productEntity));
        when(cartRepository.findByProductId(any(Long.class))).thenReturn(Optional.empty());

        cartService.addToCart(productDTO, 1);

        ArgumentCaptor<CartEntity> argumentCaptor = ArgumentCaptor.forClass(CartEntity.class);
        verify(cartRepository, times(1)).save(argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue().getQuantity());
    }

    @Test
    void addToCart_existingProductInCart_quantityIsUpdated() {
        ProductDTO productDTO = createProductDTO("Existing Product");
        ProductEntity productEntity = createProductEntity("Existing Product", 10);
        CartEntity existingCartItem = createCartEntity(productEntity, 1);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productEntity));
        when(cartRepository.findByProductId(any(Long.class))).thenReturn(Optional.of(existingCartItem));

        cartService.addToCart(productDTO, 1);

        ArgumentCaptor<CartEntity> argumentCaptor = ArgumentCaptor.forClass(CartEntity.class);
        verify(cartRepository, times(1)).save(argumentCaptor.capture());

        assertEquals(2, argumentCaptor.getValue().getQuantity());
    }

    @Test
    void checkout_cartHasItems_orderIsCreated() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartItem = createCartEntity(productEntity, 1);

        when(cartRepository.findAll()).thenReturn(List.of(cartItem));
        when(productRepository.findAllById(any())).thenReturn(List.of(productEntity));
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Order created"));

        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD; // Use a constant from the PaymentMethod enum
        cartService.checkout(paymentMethod); // Pass it to the checkout method

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void applyCampaign_validCampaignApplied_discountIsCalculatedCorrectly() {
        ProductEntity productEntity = createProductEntity("Test Product", 10);
        CartEntity cartEntity = createCartEntity(productEntity, 5);
        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setOfferType(OfferType.TEN_PERCENT_DISCOUNT);

        when(cartRepository.findAll()).thenReturn(List.of(cartEntity));
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offerEntity));

        cartService.applyCampaign(1L);

        ArgumentCaptor<CartEntity> argumentCaptor = ArgumentCaptor.forClass(CartEntity.class);
        verify(cartRepository, times(1)).save(argumentCaptor.capture());

        assertEquals(450.0, argumentCaptor.getValue().getTotalPrice());
    }
}
