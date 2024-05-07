package tyt.sales.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tyt.sales.model.OfferEntity;
import tyt.sales.service.OfferService;

@RestController
@RequestMapping("/campaign")
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/create")
    public String createCampaign(@RequestBody OfferEntity campaign) {
        return offerService.createCampaign(campaign);
    }


}
