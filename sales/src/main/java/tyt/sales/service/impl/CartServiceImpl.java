package tyt.sales.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tyt.sales.model.*;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.model.mapper.CartMapper;
import tyt.sales.model.mapper.OrderMapper;
import tyt.sales.repository.*;
import tyt.sales.rules.InsufficientStockException;
import tyt.sales.rules.ResourceNotFoundException;
import tyt.sales.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service class for managing shopping cart operations.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final WebClient webClient;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;
    private final OfferRepository offerRepository;

    private static final CartMapper cartMapper = CartMapper.INSTANCE;
    private static final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Override
    @Transactional
    public String addToCart(ProductDTO product, Integer quantity) {
        ProductEntity productEntity = productRepository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + product.getId()));

        validateStock(productEntity, quantity);

        CartEntity cartItem = cartRepository.findByProductId(product.getId())
                .orElseGet(() -> {
                    CartEntity newCartItem = new CartEntity();
                    newCartItem.setProduct(productEntity);
                    newCartItem.setQuantity(0);
                    return newCartItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartRepository.save(cartItem);

        log.info("Product added to cart: {}", product.getName());
        return "Product added to cart: " + product.getName();
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.orderDtoToOrderEntity(orderDTO);
        orderRepository.save(orderEntity);
    }

    @Override
    public String removeAllItemsFromCart() {
        cartRepository.deleteAll();
        return "All items removed from cart";
    }

    @Override
    public String removeItemFromCart(Long productId) {
        cartRepository.deleteCartItemEntityByProductId(productId);
        return "Product removed from cart";
    }

    @Override
    @Transactional
    public String checkout() {
        List<CartEntity> cartItems = cartRepository.findAll();
        if (cartItems.isEmpty()) {
            return "Cart is empty!";
        }

        OrderEntity order = createOrder(cartItems);
        orderRepository.save(order);

        // Asynchronously send the request to the record service
        webClient.post()
                .uri(String.format("/record/create/%s", order.getId()))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("Record creation failed")
                .subscribe();

        clearCart();
        log.info("Checkout successful. Order ID: {}", order.getId());
        return "Checkout successful. Order ID: " + order.getId();
    }

    private void clearCart() {
        cartRepository.deleteAll();
    }

    @Override
    public List<CartDTO> getCart() {
        List<CartEntity> cartItems = cartRepository.findAll();

        // Efficiently fetch product information and calculate totals
        Map<Long, ProductEntity> productMap = productRepository.findAllById(
                cartItems.stream().map(cartItem -> cartItem.getProduct().getId()).toList()
        ).stream().collect(Collectors.toMap(ProductEntity::getId, Function.identity()));

        cartItems.forEach(cartItem -> {
            ProductEntity product = productMap.get(cartItem.getProduct().getId());
            cartItem.setProductName(product.getName());
            cartItem.setProductPrice(product.getPrice());

            calculateAndSaveTotalPrice(cartItem);
        });

        return cartMapper.fromEntities(cartItems);
    }

    public void applyCampaign(Long cartId, Long campaignId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));

        OfferEntity offer = offerRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id " + campaignId));

        cart.setAppliedOffer(offer);
        calculateAndSaveTotalPrice(cart);
    }

    private void calculateAndSaveTotalPrice(CartEntity cartItem) {
        double originalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
        double discount = calculateDiscount(cartItem, originalPrice);

        cartItem.setTotalPrice(originalPrice - discount);
        cartRepository.save(cartItem);
    }

    private double calculateDiscount(CartEntity cartItem, double originalPrice) {
        OfferEntity offer = cartItem.getAppliedOffer();
        if (offer == null) {
            return 0.0;
        }

        switch (offer.getOfferType()) {
            case TEN_PERCENT_DISCOUNT:
                return originalPrice * 0.1;

            case BUY_THREE_PAY_TWO:
                if (cartItem.getQuantity() >= 3) {
                    int bundlesOfThree = cartItem.getQuantity() / 3;
                    return cartItem.getProduct().getPrice() * bundlesOfThree;
                }
            default:
                return 0.0; // Or handle other offer types
        }
    }

    private void validateStock(ProductEntity productEntity, Integer quantity) {
        if (productEntity.getStock() < quantity) {
            log.error("Insufficient stock for product: {}", productEntity.getName());
            throw new InsufficientStockException(productEntity.getName());
        }
    }

    private OrderEntity createOrder(List<CartEntity> cartItems) {
        OrderEntity order = new OrderEntity();
        order.setOrderDate(java.time.LocalDateTime.now());

        double totalPrice = cartItems.stream()
                .mapToDouble(CartEntity::getTotalPrice)
                .sum();
        order.setTotal(totalPrice);

        List<OrderProductEntity> orderProducts = cartItems.stream()
                .map(this::createOrderProduct)
                .toList();
        order.setOrderProducts(orderProducts);

        // Assuming you only want to apply one offer per order
        cartItems.stream()
                .filter(cartItem -> cartItem.getAppliedOffer() != null)
                .findFirst()
                .map(CartEntity::getAppliedOffer)
                .ifPresent(order::setOffer);

        log.info("Order created: {}", order);
        return order;
    }

    private OrderProductEntity createOrderProduct(CartEntity cartItem) {
        ProductEntity product = cartItem.getProduct();
        product.setStock(product.getStock() - cartItem.getQuantity());
        productRepository.save(product);

        OrderProductEntity orderProduct = new OrderProductEntity();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(cartItem.getQuantity());

        log.info("Order product created: {}", orderProduct);
        return orderProductRepository.save(orderProduct);
    }
}