package tyt.sales.service;

import org.springframework.web.bind.annotation.RequestBody;
import tyt.sales.model.OfferEntity;

public interface OfferService {

    String createCampaign(OfferEntity campaign);

    String deleteCampaign(Long campaignId);

    String updateCampaign(@RequestBody OfferEntity campaign);
}
