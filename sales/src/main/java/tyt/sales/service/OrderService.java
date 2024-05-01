package tyt.sales.service;

import tyt.sales.model.dto.OrderDTO;

import java.util.List;

/**
 * OrderService is an interface that defines the contract for the order service.
 * It provides methods to retrieve all orders and to retrieve a specific order by its ID.
 */
public interface OrderService {

    /**
     * Retrieves all orders.
     *
     * @return a list of OrderDTO objects representing all orders.
     */
    List<OrderDTO> getAllOrders();

    /**
     * Retrieves a specific order by its ID.
     *
     * @param id the ID of the order to retrieve.
     * @return an OrderDTO object representing the order with the given ID.
     */
    OrderDTO getOrderById(Long id);
}