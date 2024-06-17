package tyt.sales.service;

import tyt.sales.model.PaymentMethod;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.model.dto.ProductDTO;

import java.io.IOException;
import java.util.List;

public interface CartService {

    String addToCart(ProductDTO product, Integer quantity);

    void createOrder(OrderDTO orderDTO);

    String removeAllItemsFromCart();

    String removeItemFromCart(Long productId);


    List<CartDTO> getCart();

    String checkout(PaymentMethod paymentMethod) throws IOException;

    void applyCampaign(Long campaignId);
}
