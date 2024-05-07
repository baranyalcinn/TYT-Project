package tyt.sales.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tyt.sales.model.OfferEntity;
import tyt.sales.repository.OfferRepository;
import tyt.sales.service.OfferService;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;


    @Override
    public String createCampaign (@RequestBody OfferEntity campaign) {
        offerRepository.save(campaign);
        return "Campaign created successfully";
    }

    @Override
    public String deleteCampaign(Long campaignId) {
        offerRepository.deleteById(campaignId);
        return "Campaign deleted successfully";
    }

    @Override
    public String updateCampaign(@RequestBody OfferEntity campaign) {
        offerRepository.save(campaign);
        return "Campaign updated successfully";
    }

}

