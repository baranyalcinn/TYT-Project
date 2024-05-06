package tyt.sales.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tyt.sales.database.OfferRepository;
import tyt.sales.model.offer.OfferEntity;
import tyt.sales.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    @Override
    public String createCampaign (@RequestBody OfferEntity campaign) {
        offerRepository.save(campaign);
        return "Campaign created successfully";
    }

}

