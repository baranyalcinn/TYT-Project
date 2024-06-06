package tyt.sales.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.model.mapper.OrderMapper;
import tyt.sales.repository.OrderRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_returnsAllOrders() {
        OrderEntity entity1 = new OrderEntity();
        OrderEntity entity2 = new OrderEntity();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        Iterable<OrderDTO> result = orderService.getAllOrders();

        assertEquals(2, ((Collection<?>) result).size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_returnsOrder_whenOrderExists() {
        OrderEntity entity = new OrderEntity();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        OrderDTO result = orderService.getOrderById(1L);

        assertEquals(orderMapper.fromEntity(entity), result);
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    void getOrderById_returnsNull_whenOrderDoesNotExist() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        OrderDTO result = orderService.getOrderById(1L);

        assertNull(result);
        verify(orderRepository, times(1)).findById(anyLong());
    }
}