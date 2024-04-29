package tyt.sales.rules;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productName) {
        super("Product not found: ");
    }
}
