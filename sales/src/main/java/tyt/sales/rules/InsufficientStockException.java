package tyt.sales.rules;

import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {


    public InsufficientStockException(String productName) {
        super("Not enough stock for product: " + productName);
    }

}
