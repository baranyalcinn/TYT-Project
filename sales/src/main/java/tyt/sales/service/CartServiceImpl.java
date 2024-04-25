package tyt.sales.service;

import jakarta.transaction.Transactional;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
    public String addToCart(ProductDTO product, Integer quantity) {

        ProductEntity productEntity = productRepository.findById(product.getId()).orElse(null);
        if (productEntity == null) {
            return "Product not found";
        }

        // Optimized: Check for existing cart item using a repository query
        Optional<CartEntity> existingCartItem = cartRepository.findByProductId(product.getId());
        if (existingCartItem.isPresent()) {
            CartEntity cartItem = existingCartItem.get();

            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartRepository.save(cartItem);
        } else {
            // No change needed here:
            CartEntity newCartItem = new CartEntity();
            newCartItem.setProduct(productEntity);
            newCartItem.setQuantity(quantity);
            cartRepository.save(newCartItem);
        }

        return "Product added to cart";
    }


    @Override
    public void createOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    @Override
    public void removeItemFromCart(Long productId) {
        cartRepository.deleteCartItemEntityByProductId(productId);
    }


    @Override
    public List<CartDTO> getCart() {
        List<CartEntity> cart = cartRepository.findAll(); // Delegate getting active cart

        // Fetch necessary product information directly from the database
        cart.forEach(cartItem -> {
            ProductEntity product = cartItem.getProduct();
            cartItem.setProductName(product.getName());
            cartItem.setProductPrice(product.getPrice());
        });

        return CartDTO.fromEntities(cart);
    }


    @Override
    @Transactional // Ensure transaction management for atomicity
    public String checkout() {

        List<CartEntity> cart = cartRepository.findAll();


        // Create a new order
        OrderEntity order = new OrderEntity();


        // Calculate total price
        double totalPrice = cart.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
        order.setTotal(totalPrice);
        order.setOrderDate(new Date());

        List<OrderProductEntity> orderProducts = cart.stream()
                .map(cartItem -> {
                    ProductEntity product = cartItem.getProduct();
                    product.setStock(product.getStock() - cartItem.getQuantity()); // Update stock
                    productRepository.save(product); // Save updated product

                    OrderProductEntity orderProduct = new OrderProductEntity();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(cartItem.getQuantity());
                    return orderProductRepository.save(orderProduct); // Save OrderProductEntity to the database
                })
                .collect(Collectors.toList());
        order.setOrderProducts(orderProducts);

        orderRepository.save(order);

        cartRepository.deleteAll();

        return "Checkout successful, a new cart created";
    }


}

