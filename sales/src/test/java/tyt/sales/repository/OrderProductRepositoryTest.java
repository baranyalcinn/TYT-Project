package tyt.sales.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.OrderProductEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderProductRepositoryTest {

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private OrderProductEntity orderProductEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_HappyPath() {
        when(orderProductRepository.findById(anyLong())).thenReturn(Optional.of(orderProductEntity));
        Optional<OrderProductEntity> result = orderProductRepository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(orderProductEntity, result.get());
    }

    @Test
    void findById_OrderProductNotFound() {
        when(orderProductRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<OrderProductEntity> result = orderProductRepository.findById(1L);
        assertFalse(result.isPresent());
    }
}