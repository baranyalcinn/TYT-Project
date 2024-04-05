package tyt.sales.service;

import org.springframework.stereotype.Service;
import tyt.sales.database.CartItemRepository;
import tyt.sales.database.CartRepository;
import tyt.sales.database.OrderRepository;
import tyt.sales.database.ProductRepository;
import tyt.sales.model.CartEntity;
import tyt.sales.model.CartItemEntity;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, OrderRepository orderRepository,
                           ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    public String addToCart(ProductDTO product, Integer quantity) {

        List<CartEntity> carts = cartRepository.findAll();
        CartEntity cart;
        if (carts.isEmpty()) {
            // If there are no carts, create a new one
            cart = new CartEntity();
            cartRepository.save(cart);
        } else {
            // If there are carts, return the first one
            cart = carts.get(0);
        }

        // Find the product in the database
        ProductEntity productEntity = productRepository.findById(product.getId()).orElse(null);
        if (productEntity == null) {
            return "Product not found";
        }

        // Check if the product is already in the cart
        List<CartItemEntity> cartItems = cart.getCartItems();
        for (CartItemEntity cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                // If the product is already in the cart, increase the quantity
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemRepository.save(cartItem);
                return "Product added to cart";
            }
        }
        // If the product is not in the cart, create a new cart item
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(productEntity);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return "Product added to cart";
    }

    @Override
    public void createOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }


    @Override
    public CartDTO getCart() {
        List<CartEntity> carts = cartRepository.findAll();
        CartEntity cart;
        if (carts.isEmpty()) {
            // If there are no carts, create a new one
            cart = new CartEntity();
            cartRepository.save(cart);
        } else {
            // If there are carts, return the first one
            cart = carts.get(0);
        }
        // Set each item's name, price, quantity, and total price
        for (CartItemEntity cartItem : cart.getCartItems()) {
            ProductEntity product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            cartItem.setProductName(product.getName());
            cartItem.setProductPrice(product.getPrice());


        }
        return CartDTO.fromEntity(cart);
    }

   @Override
public String checkout() {
    List<CartEntity> carts = cartRepository.findAll();
    if (carts.isEmpty()) {
        return "Cart is empty";
    }
    CartEntity cart = carts.get(0);
    List<CartItemEntity> cartItems = cart.getCartItems();
    if (cartItems.isEmpty()) {
        return "Cart is empty";
    }
    // Create a new order
    OrderEntity order = new OrderEntity();
    order.setCart(cart);
    // Calculate total price
    double totalPrice = cartItems.stream()
            .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
            .sum();
    order.setTotal(totalPrice); // Set total price to order
    orderRepository.save(order);
    // Clear the cart
    cart.setCartItems(null);
    cartRepository.save(cart);
    return "Checkout successful";
}


}

