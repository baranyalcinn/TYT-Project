package tyt.sales.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.OrderEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEntity orderEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_HappyPath() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        Optional<OrderEntity> result = orderRepository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(orderEntity, result.get());
    }

    @Test
    void findById_OrderNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<OrderEntity> result = orderRepository.findById(1L);
        assertFalse(result.isPresent());
    }
}