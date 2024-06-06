package tyt.sales.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.service.OrderService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_returnsAllOrders() {
        OrderDTO order1 = new OrderDTO();
        OrderDTO order2 = new OrderDTO();
        List<OrderDTO> expectedOrders = Arrays.asList(order1, order2);
        when(orderService.getAllOrders()).thenReturn(expectedOrders);

        List<OrderDTO> actualOrders = orderController.getAllOrders();

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void getAllOrders_noOrders_returnsEmptyList() {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        List<OrderDTO> actualOrders = orderController.getAllOrders();

        assertEquals(Collections.emptyList(), actualOrders);
    }

    @Test
    void getOrderById_existingId_returnsOrder() {
        OrderDTO expectedOrder = new OrderDTO();
        when(orderService.getOrderById(1L)).thenReturn(expectedOrder);

        OrderDTO actualOrder = orderController.getOrderById(1L);

        assertEquals(expectedOrder, actualOrder);
    }
}