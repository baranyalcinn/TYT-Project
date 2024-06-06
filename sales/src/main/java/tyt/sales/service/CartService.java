package tyt.sales.service;

import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.model.dto.ProductDTO;

import java.io.IOException;
import java.util.List;

/**
 * CartService is an interface that defines the operations that can be performed on a shopping cart.
 */
public interface CartService {

    String addToCart(ProductDTO product, Integer quantity);

    void createOrder(OrderDTO orderDTO);

    String removeAllItemsFromCart();

    String removeItemFromCart(Long productId);


    List<CartDTO> getCart();

    String checkout() throws IOException;

    void applyCampaign(Long cartId, Long campaignId);
}
