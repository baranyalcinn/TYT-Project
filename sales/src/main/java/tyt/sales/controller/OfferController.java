package tyt.sales.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tyt.sales.model.OfferEntity;
import tyt.sales.service.OfferService;

@RestController
@RequestMapping("/campaign")
@AllArgsConstructor
@Log4j2
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/create")
    public String createCampaign(@RequestBody OfferEntity campaign) {
        log.info("Request to create campaign: {}", campaign);
        return offerService.createCampaign(campaign);
    }


}
