package tyt.sales.service;


import tyt.sales.model.OrderEntity;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;


public interface CartService {
    String addToCart(ProductDTO product, Integer quantity);

    void createOrder(OrderEntity order);

    void removeItemFromCart(Long cartItemId);

    CartDTO getCart();

    String checkout();
}
