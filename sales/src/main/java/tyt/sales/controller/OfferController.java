package tyt.sales.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tyt.sales.model.offer.OfferEntity;
import tyt.sales.service.OfferService;

@RestController
@RequestMapping("/campaign")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/create")
    public String createCampaign(@RequestBody OfferEntity campaign) {
        return offerService.createCampaign(campaign);
    }


}
