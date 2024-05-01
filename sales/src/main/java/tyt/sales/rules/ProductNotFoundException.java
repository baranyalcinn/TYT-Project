package tyt.sales.rules;

import lombok.Getter;

/**
 * This class represents a custom exception that is thrown when a product is not found.
 * It extends the RuntimeException class, meaning it's an unchecked exception.
 * The class is annotated with @Getter from the Lombok library, which automatically generates getter methods for all fields.
 */
@Getter
public class ProductNotFoundException extends RuntimeException {
    /**
     * Constructor for the ProductNotFoundException class.
     * It calls the superclass constructor (RuntimeException) and passes a custom message to it.
     *
     * @param productName The name of the product that was not found. Currently, this parameter is not used in the message.
     */
    public ProductNotFoundException(String productName) {
        super("Product not found: ");
    }
}