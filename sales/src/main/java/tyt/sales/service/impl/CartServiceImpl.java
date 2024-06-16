package tyt.sales.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
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
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service class for managing shopping cart operations.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final WebClient.Builder webClient;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;
    private final OfferRepository offerRepository;

    private static final CartMapper cartMapper = CartMapper.INSTANCE;
    private static final OrderMapper orderMapper = OrderMapper.INSTANCE;

    /**
     * Adds a product to the cart.
     *
     * @param product  The product to add.
     * @param quantity The quantity of the product to add.
     * @return A message indicating the product was added.
     */
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

    /**
     * Creates an order.
     *
     * @param orderDTO The order to create.
     */
    @Override
    public void createOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.orderDtoToOrderEntity(orderDTO);
        orderRepository.save(orderEntity);
    }

    /**
     * Removes all items from the cart.
     *
     * @return A message indicating all items were removed.
     */
    @Override
    public String removeAllItemsFromCart() {
        cartRepository.deleteAll();
        return "All items removed from cart";
    }

    /**
     * Removes an item from the cart.
     *
     * @param productId The ID of the product to remove.
     * @return A message indicating the product was removed.
     */
    @Override
    public String removeItemFromCart(Long productId) {
        cartRepository.deleteCartItemEntityByProductId(productId);
        return "Product removed from cart";
    }

    /**
     * Checks out the cart, creating an order and clearing the cart.
     *
     * @return A message indicating the checkout was successful.
     */
    @Transactional
    @Override
    public String checkout() {
        List<CartEntity> cartItems = cartRepository.findAll();
        if (cartItems.isEmpty()) {
            return "Cart is empty!";
        }

        OrderEntity order = createOrder(cartItems);
        orderRepository.save(order);

        String orderId = String.valueOf(order.getId());

        webClient.build()
                .post()
                .uri(String.format("http://record-service/record/create/%s", orderId))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    log.error("Record creation failed. Error: {}", throwable.getMessage());
                    return Mono.just("Record creation failed");
                })
                .subscribe(recordResponse -> {
                    log.info("Record creation response: {}", recordResponse);
                    clearCart();
                    log.info("Checkout successful. Order ID: {}", orderId);
                });

        return "Checkout successful. Order ID: " + orderId + ". Record creation response will be logged.";
    }


    private void clearCart() {
        cartRepository.deleteAll();
    }

    /**
     * Gets the current state of the cart.
     *
     * @return A list of cart items.
     */
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

    /**
     * Applies a campaign to a cart.
     *
     * @param campaignId The ID of the campaign.
     */
    @Transactional
    public void applyCampaign(Long campaignId) {
        List<CartEntity> carts = cartRepository.findAll();
        if (carts.isEmpty()) {
            throw new ResourceNotFoundException("No cart found");
        }
        CartEntity cart = carts.get(0);

        OfferEntity offer = offerRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id " + campaignId));

        cart.setAppliedOffer(offer);
        calculateAndSaveTotalPrice(cart);
    }

    /**
     * Calculates and saves the total price of a cart item.
     *
     * @param cartItem The cart item.
     */
    private void calculateAndSaveTotalPrice(CartEntity cartItem) {
        double originalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
        double discount = calculateDiscount(cartItem, originalPrice);

        cartItem.setTotalPrice(originalPrice - discount);
        cartRepository.save(cartItem);
    }

    /**
     * Calculates the discount for a cart item.
     *
     * @param cartItem      The cart item.
     * @param originalPrice The original price of the cart item.
     * @return The discount.
     */
    private double calculateDiscount(CartEntity cartItem, double originalPrice) {
        OfferEntity offer = cartItem.getAppliedOffer();
        if (offer == null) {
            return 0.0;
        }

        // Return 0 if offer applies but condition not met
        return switch (offer.getOfferType()) {
            case TEN_PERCENT_DISCOUNT -> originalPrice * 0.1;
            case BUY_THREE_PAY_TWO -> {
                if (cartItem.getQuantity() >= 3) {
                    int bundlesOfThree = cartItem.getQuantity() / 3;
                    yield cartItem.getProduct().getPrice() * bundlesOfThree;
                }
                yield 0.0;
            }
        };
    }

    /**
     * Validates the stock of a product.
     *
     * @param productEntity The product.
     * @param quantity      The quantity to validate.
     */
    private void validateStock(ProductEntity productEntity, Integer quantity) {
        if (productEntity.getStock() < quantity) {
            log.error("Insufficient stock for product: {}", productEntity.getName());
            throw new InsufficientStockException(productEntity.getName());
        }
    }

    /**
     * Creates an order from a list of cart items.
     *
     * @param cartItems The cart items.
     * @return The created order.
     */
    private OrderEntity createOrder(List<CartEntity> cartItems) {
        OrderEntity order = new OrderEntity();
        order.setOrderDate(java.time.LocalDateTime.now());

        double totalPrice = cartItems.stream()
                .mapToDouble(CartEntity::getTotalPrice)
                .sum();
        order.setTotal(totalPrice);

        List<OrderProductEntity> orderProducts = cartItems.stream()
                .map(cartItem -> createOrderProduct(cartItem, order))
                .toList();
        order.setOrderProducts(orderProducts);

        OfferEntity appliedOffer = cartItems.stream()
                .map(CartEntity::getAppliedOffer)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        order.setOffer(appliedOffer);

        log.info("Order created: {}", order);
        return order;
    }


    /**
     * Creates an order product from a cart item and an order.
     *
     * @param cartItem The cart item.
     * @param order    The order.
     * @return The created order product.
     */
    private OrderProductEntity createOrderProduct(CartEntity cartItem, OrderEntity order) {
        ProductEntity product = cartItem.getProduct();
        product.setStock(product.getStock() - cartItem.getQuantity());
        productRepository.save(product);

        OrderProductEntity orderProduct = new OrderProductEntity();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(cartItem.getQuantity());
        orderProduct.setOrder(order);

        log.info("Order product created: {}", orderProduct);
        return orderProductRepository.save(orderProduct);
    }
}