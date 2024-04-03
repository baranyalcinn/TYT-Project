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

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


@Override
public void addToCart(ProductEntity product, Integer quantity) {
    // Get the current cart
    CartEntity cart = getCart();
    if (cart == null) {
        // If there is no cart, create a new one
        cart = new CartEntity();
        cartRepository.save(cart);
    }

    // Create a new CartItemEntity and set its product and quantity
    CartItemEntity cartItem = new CartItemEntity();
    cartItem.setProduct(product);
    cartItem.setQuantity(quantity);

    // Add the CartItemEntity to the cart's cartItems list
    cart.getCartItems().add(cartItem);

    // Save the cart
    cartRepository.save(cart);
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
    public CartEntity getCart() {
        List<CartEntity> carts = cartRepository.findAll();
        if (carts.isEmpty()) {
            // If there are no carts, create a new one
            CartEntity newCart = new CartEntity();
            cartRepository.save(newCart);
            return newCart;
        } else {
            // If there are carts, return the first one
            return carts.get(0);
        }
    }
}