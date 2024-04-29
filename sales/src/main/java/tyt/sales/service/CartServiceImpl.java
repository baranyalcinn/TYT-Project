package tyt.sales.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tyt.sales.database.CartRepository;
import tyt.sales.database.OrderProductRepository;
import tyt.sales.database.OrderRepository;
import tyt.sales.database.ProductRepository;
import tyt.sales.model.CartEntity;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.OrderProductEntity;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.rules.InsufficientStockException;
import tyt.sales.rules.ProductNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Log4j2
public class CartServiceImpl implements CartService {


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;

    public CartServiceImpl(OrderRepository orderRepository,
                           ProductRepository productRepository, CartRepository cartRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderProductRepository = orderProductRepository;
    }


    @Override
    @Transactional
    public String addToCart(ProductDTO product, Integer quantity) {
        ProductEntity productEntity = productRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));

        if (productEntity.getStock() < quantity) {
            throw new InsufficientStockException(product.getName());
        }

        Optional<CartEntity> existingCartItem = cartRepository.findByProductId(product.getId());
        CartEntity cartItem = existingCartItem.orElseGet(() -> {
            CartEntity newCartItem = new CartEntity();
            newCartItem.setProduct(productEntity);
            newCartItem.setQuantity(quantity); // Set quantity directly
            return newCartItem;
        });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartRepository.save(cartItem);

        return "Product added to cart: " + product.getName();
    }



    @Override
    public void createOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    @Override
    public String removeAllItemsFromCart() {
        cartRepository.deleteAll();
        return "All items removed from cart";
    }

    @Override
    public String removeItemFromCart(Long productId) {
        cartRepository.deleteCartItemEntityByProductId(productId);
        return null;
    }

    @Override
    public List<CartDTO> getCart() {
        List<CartEntity> cart = cartRepository.findAll();
        Map<Long, ProductEntity> productMap = getProductMap(cart);
        assignProductInfoToCartItems(cart, productMap);
        return CartDTO.fromEntities(cart);
    }


    @Override
    @Transactional
    public String checkout() {
        List<CartEntity> cart = cartRepository.findAll();
        OrderEntity order = new OrderEntity();
        double totalPrice = calculateTotalPrice(cart);
        order.setTotal(totalPrice);
        order.setOrderDate(new Date());
        List<OrderProductEntity> orderProducts = createOrderProducts(cart, order);
        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
        cartRepository.deleteAll();
        return "Checkout successful, a new cart created";
    }


    private double calculateTotalPrice(List<CartEntity> cart) {
        return cart.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
    }

    private List<OrderProductEntity> createOrderProducts(List<CartEntity> cart, OrderEntity order) {
        return cart.stream()
                .map(cartItem -> createOrderProduct(cartItem, order))
                .collect(Collectors.toList());
    }

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

    private Map<Long, ProductEntity> getProductMap(List<CartEntity> cart) {
        List<Long> productIds = cart.stream()
                .map(cartItem -> cartItem.getProduct().getId())
                .collect(Collectors.toList());
        return productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));
    }

    private void assignProductInfoToCartItems(List<CartEntity> cart, Map<Long, ProductEntity> productMap) {
        cart.forEach(cartItem -> {
            ProductEntity product = productMap.get(cartItem.getProduct().getId());
            cartItem.setProductName(product.getName());
            cartItem.setProductPrice(product.getPrice());
        });
    }


}

