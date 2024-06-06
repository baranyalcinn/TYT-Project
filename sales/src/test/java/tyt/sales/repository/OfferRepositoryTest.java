package tyt.sales.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.OfferEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfferRepositoryTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferEntity offerEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_HappyPath() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offerEntity));
        Optional<OfferEntity> result = offerRepository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(offerEntity, result.get());
    }

    @Test
    void findById_OfferNotFound() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<OfferEntity> result = offerRepository.findById(1L);
        assertFalse(result.isPresent());
    }
}