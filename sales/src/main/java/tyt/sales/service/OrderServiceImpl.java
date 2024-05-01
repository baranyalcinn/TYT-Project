package tyt.sales.service;

import org.springframework.stereotype.Service;
import tyt.sales.database.OrderRepository;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.dto.OrderDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to Orders.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    /**
     * Constructor for OrderServiceImpl.
     *
     * @param orderRepository the repository to be used for fetching and storing Order data
     */
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Fetches all orders from the database and converts them to DTOs.
     *
     * @return a list of OrderDTO objects
     */
    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Fetches an order by its ID from the database and converts it to a DTO.
     *
     * @param id the ID of the order to fetch
     * @return an OrderDTO object if an order with the given ID exists, null otherwise
     */
    @Override
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderDTO::fromEntity)
                .orElse(null);
    }
}