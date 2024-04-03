package tyt.sales.service;


import tyt.sales.model.CartEntity;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.ProductEntity;


public interface CartService {
    void addToCart(ProductEntity product, Integer quantity);

    void createOrder(OrderEntity order);

    void removeItemFromCart(Long cartItemId);

    CartEntity getCart();
}
