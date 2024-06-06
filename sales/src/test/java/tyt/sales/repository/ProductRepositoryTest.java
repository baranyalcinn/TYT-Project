package tyt.sales.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.ProductEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductEntity productEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_HappyPath() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));
        Optional<ProductEntity> result = productRepository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(productEntity, result.get());
    }

    @Test
    void findById_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<ProductEntity> result = productRepository.findById(1L);
        assertFalse(result.isPresent());
    }
}