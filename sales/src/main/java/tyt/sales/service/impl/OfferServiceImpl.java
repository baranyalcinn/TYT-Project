package tyt.sales.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tyt.sales.model.OfferEntity;
import tyt.sales.repository.OfferRepository;
import tyt.sales.service.OfferService;

/**
 * This is a service class that implements the OfferService interface.
 * It provides methods to create, delete, and update campaigns.
 */
@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    // Repository to perform operations on the OfferEntity
    private final OfferRepository offerRepository;

    /**
     * This method is used to create a new campaign.
     *
     * @param campaign This is the campaign to be created.
     * @return String This returns a success message after creating the campaign.
     */
    @Override
    public String createCampaign(@RequestBody OfferEntity campaign) {
        offerRepository.save(campaign);
        return "Campaign created successfully";
    }

    /**
     * This method is used to delete an existing campaign.
     *
     * @param campaignId This is the id of the campaign to be deleted.
     * @return String This returns a success message after deleting the campaign.
     */
    @Override
    public String deleteCampaign(Long campaignId) {
        offerRepository.deleteById(campaignId);
        return "Campaign deleted successfully";
    }

    /**
     * This method is used to update an existing campaign.
     *
     * @param campaign This is the campaign to be updated.
     * @return String This returns a success message after updating the campaign.
     */
    @Override
    public String updateCampaign(@RequestBody OfferEntity campaign) {
        offerRepository.save(campaign);
        return "Campaign updated successfully";
    }

}