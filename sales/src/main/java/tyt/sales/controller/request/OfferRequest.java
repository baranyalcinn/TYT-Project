package tyt.sales.controller.request;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OfferRequest {

    String name;
    String description;
    LocalDateTime startDate;
    LocalDateTime endDate;

}
