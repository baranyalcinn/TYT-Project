package tyt.record.model;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH("Cash"),
    CREDIT_CARD("Credit Card");

    private final String name;

    PaymentMethod(String name) {
        this.name = name;
    }

}
