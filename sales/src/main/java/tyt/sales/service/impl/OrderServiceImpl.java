package tyt.sales.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.model.mapper.OrderMapper;
import tyt.sales.repository.OrderRepository;
import tyt.sales.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to Orders.
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    private final OrderRepository orderRepository;


    /**
     * Fetches all orders from the database and converts them to DTOs.
     *
     * @return a list of OrderDTO objects
     */
    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::fromEntity)
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
                .map(orderMapper::fromEntity)
                .orElse(null);
    }


}