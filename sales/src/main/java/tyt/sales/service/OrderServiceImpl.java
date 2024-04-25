package tyt.sales.service;

import org.springframework.stereotype.Service;
import tyt.sales.database.OrderRepository;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.dto.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderDto::fromEntity)
                .orElse(null);
    }
}