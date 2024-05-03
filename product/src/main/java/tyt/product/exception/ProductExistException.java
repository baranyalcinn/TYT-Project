package tyt.product.exception;

/**
 * This class represents a custom exception that is thrown when a product already exists.
 * It extends the RuntimeException class, meaning it's an unchecked exception.
 */
public class ProductExistException extends RuntimeException{

    /**
     * Constructor for the ProductExistException class.
     *
     * @param message The detail message. The detail message is saved for
     *                later retrieval by the Throwable.getMessage() method.
     */
    public ProductExistException(String message){
        super(message);
    }
}