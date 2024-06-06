package tyt.sales.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.OfferEntity;
import tyt.sales.service.OfferService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OfferControllerTest {

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferController offerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCampaign_validOffer_returnsSuccessMessage() {
        OfferEntity offerEntity = new OfferEntity();
        when(offerService.createCampaign(offerEntity)).thenReturn("Campaign created successfully");

        String response = offerController.createCampaign(offerEntity);

        assertEquals("Campaign created successfully", response);
    }

    @Test
    void createCampaign_invalidOffer_returnsErrorMessage() {
        OfferEntity offerEntity = new OfferEntity();
        when(offerService.createCampaign(offerEntity)).thenReturn("Error: Invalid campaign details");

        String response = offerController.createCampaign(offerEntity);

        assertEquals("Error: Invalid campaign details", response);
    }
}