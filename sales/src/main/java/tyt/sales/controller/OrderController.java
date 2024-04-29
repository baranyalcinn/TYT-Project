package tyt.sales.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.service.OrderService;

import java.util.List;

/**
 * This is a Rest Controller for managing orders.
 * It provides endpoints for fetching all orders and fetching a specific order by its id.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /**
     * Constructor for OrderController.
     *
     * @param orderService The service to be used for order operations.
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * This endpoint returns all orders.
     *
     * @return A list of all orders.
     */
    @GetMapping("/all")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * This endpoint returns a specific order by its id.
     *
     * @param id The id of the order to be fetched.
     * @return The order with the given id.
     */
    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}