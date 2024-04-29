package tyt.sales.service;

import tyt.sales.model.OrderEntity;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;

import java.util.List;

/**
 * CartService is an interface that defines the operations that can be performed on a shopping cart.
 */
public interface CartService {

    /**
     * Adds a product to the cart.
     *
     * @param product  The product to be added to the cart.
     * @param quantity The quantity of the product to be added.
     * @return A string message indicating the result of the operation.
     */
    String addToCart(ProductDTO product, Integer quantity);

    /**
     * Creates an order.
     *
     * @param order The order to be created.
     */
    void createOrder(OrderEntity order);

    /**
     * Removes all items from the cart.
     *
     * @return A string message indicating the result of the operation.
     */
    String removeAllItemsFromCart();

    /**
     * Removes a specific item from the cart.
     *
     * @param productId The ID of the product to be removed.
     * @return
     */
    String removeItemFromCart(Long productId);

    /**
     * Retrieves the current state of the cart.
     *
     * @return A list of CartDTO objects representing the items in the cart.
     */
    List<CartDTO> getCart();

    /**
     * Checks out the items in the cart.
     *
     * @return A string message indicating the result of the operation.
     */
    String checkout();
}