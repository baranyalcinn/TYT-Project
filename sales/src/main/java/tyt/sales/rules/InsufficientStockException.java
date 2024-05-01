package tyt.sales.rules;

import lombok.Getter;

/**
 * This class represents an exception that is thrown when there is insufficient stock for a product.
 * It extends the RuntimeException class, meaning it's an unchecked exception.
 * The class uses Lombok's @Getter annotation to automatically generate getter methods.
 */
@Getter
public class InsufficientStockException extends RuntimeException {

    /**
     * Constructor for the InsufficientStockException class.
     * It takes a product name as an argument and constructs a detailed error message.
     *
     * @param productName The name of the product for which stock is insufficient.
     */
    public InsufficientStockException(String productName) {
        super("Not enough stock for product: " + productName);
    }
}