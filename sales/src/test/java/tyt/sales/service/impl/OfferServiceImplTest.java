package tyt.sales.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.sales.model.OfferEntity;
import tyt.sales.repository.OfferRepository;

import static org.mockito.Mockito.*;

class OfferServiceImplTest {

    @Mock
    OfferRepository offerRepository;

    @InjectMocks
    OfferServiceImpl offerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCampaignSuccessfully() {
        OfferEntity campaign = new OfferEntity();
        when(offerRepository.save(any(OfferEntity.class))).thenReturn(campaign);

        offerService.createCampaign(campaign);

        verify(offerRepository, times(1)).save(campaign);
    }

    @Test
    void deleteCampaignSuccessfully() {
        Long campaignId = 1L;

        doNothing().when(offerRepository).deleteById(campaignId);

        offerService.deleteCampaign(campaignId);

        verify(offerRepository, times(1)).deleteById(campaignId);
    }

    @Test
    void updateCampaignSuccessfully() {
        OfferEntity campaign = new OfferEntity();
        when(offerRepository.save(any(OfferEntity.class))).thenReturn(campaign);

        offerService.updateCampaign(campaign);

        verify(offerRepository, times(1)).save(campaign);
    }
}