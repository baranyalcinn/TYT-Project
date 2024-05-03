package tyt.product.exception;

/**
 * This class represents a custom exception that is thrown when a product is not found.
 * It extends the RuntimeException class, meaning it's an unchecked exception.
 * Unchecked exceptions are exceptions that can be thrown during the execution of the program.
 */
public class NoSuchProductException extends RuntimeException {

    /**
     * Constructor for the ProductNotFoundException class.
     *
     * @param message The detail message. The detail message is saved for
     *                later retrieval by the Throwable.getMessage() method.
     */
    public NoSuchProductException(String message) {
        super(message);
    }
}