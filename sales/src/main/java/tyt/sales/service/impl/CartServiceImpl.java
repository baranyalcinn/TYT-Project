package tyt.sales.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tyt.sales.database.*;
import tyt.sales.model.CartEntity;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.OrderProductEntity;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.model.mapper.CartMapper;
import tyt.sales.model.mapper.OrderMapper;
import tyt.sales.model.mapper.ProductMapper;
import tyt.sales.model.OfferEntity;
import tyt.sales.rules.InsufficientStockException;
import tyt.sales.rules.ResourceNotFoundException;
import tyt.sales.service.CartService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service class for managing shopping cart operations.
 */
@Service
@Log4j2
public class CartServiceImpl implements CartService {


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;
    private final OfferRepository offerRepository;

    private final CartMapper cartMapper = CartMapper.INSTANCE;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;


    /**
     * Constructor for CartServiceImpl.
     *
     * @param orderRepository        the repository for orders
     * @param productRepository      the repository for products
     * @param cartRepository         the repository for cart items
     * @param orderProductRepository the repository for order products
     */
    public CartServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CartRepository cartRepository, OrderProductRepository orderProductRepository, OfferRepository offerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderProductRepository = orderProductRepository;
        this.offerRepository = offerRepository;
    }

    /**
     * Adds a product to the cart.
     *
     * @param product  the product to add
     * @param quantity the quantity of the product to add
     * @return a message indicating the product has been added
     */
    @Override
    @Transactional
    public String addToCart(ProductDTO product, Integer quantity) {
        ProductEntity productEntity = productMapper.toEntity(product);
        validateStock(productEntity, quantity);
        CartEntity cartItem = getCartItem(product, productEntity, quantity);
        cartRepository.save(cartItem);
        return "Product added to cart: " + productMapper.fromEntity(productEntity).getName();
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.orderDtoToOrderEntity(orderDTO);
        orderRepository.save(orderEntity);
    }

    /**
     * Removes all items from the cart.
     *
     * @return a message indicating all items have been removed
     */
    @Override
    public String removeAllItemsFromCart() {
        cartRepository.deleteAll();
        return "All items removed from cart";
    }

    /**
     * Removes an item from the cart.
     *
     * @param productId the id of the product to remove
     * @return null
     */
    @Override
    public String removeItemFromCart(Long productId) {
        cartRepository.deleteCartItemEntityByProductId(productId);
        return null;
    }

    /**
     * Checks out the cart, creating an order and emptying the cart.
     *
     * @return a message indicating the checkout was successful
     */
    @Override
    @Transactional
    public String checkout() {
        List<CartEntity> cart = cartRepository.findAll();
        List<CartDTO> cartDTOs = cartMapper.fromEntities(cart);
        log.info("Checking out the following cart: {}", cartDTOs);
        OrderEntity order = createOrder(cart);
        orderRepository.save(order);
        cartRepository.deleteAll();
        return "Checkout successful, a new cart created";
    }
    /**
     * Gets all items in the cart.
     *
     * @return a list of cart items
     */
    // CartServiceImpl.java
    @Override
    public List<CartDTO> getCart() {
        List<CartEntity> cart = cartRepository.findAll();

        // Obtain product information
        List<Long> productIds = cart.stream()
                .map(cartItem -> cartItem.getProduct().getId())
                .collect(Collectors.toList());
        Map<Long, ProductEntity> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));


        // Update cart items with product info and calculate total for each item
        cart.forEach(cartItem -> {
            ProductEntity product = productMap.get(cartItem.getProduct().getId());
            cartItem.setProductName(product.getName());
            cartItem.setProductPrice(product.getPrice());

            if (cartItem.getAppliedOffer() == null) {
                cartItem.setTotalPrice(product.getPrice() * cartItem.getQuantity()); // Calculate total for each item
            } else {
                applyCampaignAndUpdateTotalPrice(cartItem.getId(), cartItem.getAppliedOffer().getId());
            }

        });

        return cartMapper.fromEntities(cart);
    }


    public void applyCampaign(Long cartId, Long campaignId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));

        OfferEntity offer = offerRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id " + campaignId));

        // Apply the offer
        cart.setAppliedOffer(offer);

        // Calculate discount and total efficiently
        double originalPrice = cart.getProduct().getPrice() * cart.getQuantity();
        double discount = 0.0;

        if (offer != null) {
            switch (offer.getOfferType()) {
                case TEN_PERCENT_DISCOUNT:
                    discount = originalPrice * 0.1;
                    break;
                case BUY_THREE_PAY_TWO:
                    if (cart.getQuantity() >= 3) {
                        int bundlesOfThree = cart.getQuantity() / 3;
                        int remainder = cart.getQuantity() % 3;
                        double priceForThreeAsTwo = bundlesOfThree * 2 * cart.getProduct().getPrice();
                        double priceForRemainder = remainder * cart.getProduct().getPrice();
                        discount = priceForThreeAsTwo + priceForRemainder - originalPrice;
                    }
                    break;
            }
        }

        cart.setTotalPrice(originalPrice - discount);
        cartRepository.save(cart);
    }

    private void applyCampaignAndUpdateTotalPrice(Long cartId, Long offerId) {
        applyCampaign(cartId, offerId);
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));
        double totalPrice = cart.getTotalPrice();
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }

    /**
 * Validates if the stock of a product is sufficient for the requested quantity.
 *
 * @param productEntity the ProductEntity object to check the stock of
 * @param quantity the quantity to check against the product's stock
 * @throws InsufficientStockException if the product's stock is less than the requested quantity
 */
private void validateStock(ProductEntity productEntity, Integer quantity) {
    if (productEntity.getStock() < quantity) {
        throw new InsufficientStockException(productEntity.getName());
    }
}

/**
 * Retrieves a cart item for a given product and quantity. If the cart item does not exist, a new one is created.
 *
 * @param product the ProductDTO object containing the ID of the product
 * @param productEntity the ProductEntity object of the product
 * @param quantity the quantity of the product to add to the cart
 * @return the CartEntity object representing the cart item
 */
private CartEntity getCartItem(ProductDTO product, ProductEntity productEntity, Integer quantity) {
    Optional<CartEntity> existingCartItem = cartRepository.findByProductId(product.getId());
    CartEntity cartItem = existingCartItem.orElseGet(() -> cartMapper.toEntity(product, productEntity));
    cartItem.setQuantity(cartItem.getQuantity() + quantity);
    return cartItem;
}

    /**
 * Creates a new order from a list of cart items.
 *
 * @param cart the list of CartEntity objects to create the order from
 * @return the newly created OrderEntity object
 */

private OrderEntity createOrder(List<CartEntity> cart) {
    OrderEntity order = new OrderEntity();
    order.setTotal(calculateTotalPrice(cart));
    LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    order.setOrderDate(localDateTime);
    order.setOrderProducts(createOrderProducts(cart, order));

    cart.forEach(cartItem -> {
        if (cartItem.getAppliedOffer() != null) {
            order.setOffer(cartItem.getAppliedOffer());
        }
    });

    return order;
}


/**
 * Calculates the total price of a list of cart items.
 *
 * @param cart the list of CartEntity objects to calculate the total price of
 * @return the total price of the cart items
 */
private double calculateTotalPrice(List<CartEntity> cart) {
    return cart.stream()
            .mapToDouble(CartEntity::getTotalPrice)
            .sum();
}
/**
 * Creates a list of OrderProductEntity objects from a list of cart items and an order.
 *
 * @param cart the list of CartEntity objects to create the OrderProductEntity objects from
 * @param order the OrderEntity object to associate the OrderProductEntity objects with
 * @return the list of newly created OrderProductEntity objects
 */
private List<OrderProductEntity> createOrderProducts(List<CartEntity> cart, OrderEntity order) {
    return cart.stream()
            .map(cartItem -> createOrderProduct(cartItem, order))
            .collect(Collectors.toList());
}

/**
 * Creates a new OrderProductEntity object from a cart item and an order.
 *
 * @param cartItem the CartEntity object to create the OrderProductEntity object from
 * @param order the OrderEntity object to associate the OrderProductEntity object with
 * @return the newly created OrderProductEntity object
 */
private OrderProductEntity createOrderProduct(CartEntity cartItem, OrderEntity order) {
    ProductEntity product = cartItem.getProduct();
    product.setStock(product.getStock() - cartItem.getQuantity());
    productRepository.save(product);
    OrderProductEntity orderProduct = new OrderProductEntity();
    orderProduct.setOrder(order);
    orderProduct.setProduct(product);
    orderProduct.setQuantity(cartItem.getQuantity());
    return orderProductRepository.save(orderProduct);
}


}

