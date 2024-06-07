package tyt.record.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tyt.record.model.OrderEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEntity orderEntity;

    @BeforeEach
    public void setup() {
        orderEntity = new OrderEntity();
        orderEntity.setId(1L);
    }

    @Test
    void saveOrderShouldReturnSavedOrder() {
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        assertEquals(savedOrder, orderEntity);
    }

    @Test
    void findOrderByIdShouldReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        Optional<OrderEntity> foundOrder = orderRepository.findById(1L);
        assertTrue(foundOrder.isPresent());
        assertEquals(foundOrder.get(), orderEntity);
    }

    @Test
    void findOrderByIdShouldReturnEmptyWhenOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<OrderEntity> foundOrder = orderRepository.findById(1L);
        assertTrue(foundOrder.isEmpty());
    }
}