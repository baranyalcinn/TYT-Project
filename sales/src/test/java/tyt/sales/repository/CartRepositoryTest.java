package tyt.sales.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import tyt.sales.model.CartEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureDataJpa
class CartRepositoryTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartEntity cartEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteCartItemEntityByProductId_HappyPath() {
        doNothing().when(cartRepository).deleteCartItemEntityByProductId(anyLong());
        cartRepository.deleteCartItemEntityByProductId(1L);
        verify(cartRepository, times(1)).deleteCartItemEntityByProductId(1L);
    }

    @Test
    void findByProductId_HappyPath() {
        when(cartRepository.findByProductId(anyLong())).thenReturn(Optional.of(cartEntity));
        Optional<CartEntity> result = cartRepository.findByProductId(1L);
        assertTrue(result.isPresent());
        assertEquals(cartEntity, result.get());
    }

    @Test
    void findByProductId_ProductNotFound() {
        when(cartRepository.findByProductId(anyLong())).thenReturn(Optional.empty());
        Optional<CartEntity> result = cartRepository.findByProductId(1L);
        assertFalse(result.isPresent());
    }
}