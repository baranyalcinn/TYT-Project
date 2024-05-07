package tyt.sales.model;

import lombok.Getter;

@Getter
public enum OfferType {
    BUY_THREE_PAY_TWO("BUY_THREE_PAY_TWO"),
    TEN_PERCENT_DISCOUNT("TEN_PERCENT_DISCOUNT");

    private final String name;

    OfferType(String name) {
        this.name = name;
    }
}