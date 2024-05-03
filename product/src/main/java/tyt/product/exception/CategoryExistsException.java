package tyt.product.exception;

/**
 * This class represents a custom exception that is thrown when a category already exists.
 * It extends the RuntimeException class, meaning it's an unchecked exception.
 */
public class CategoryExistsException extends RuntimeException{

    /**
     * Constructor for the CategoryExistsException class.
     *
     * @param message The detail message. The detail message is saved for
     *                later retrieval by the Throwable.getMessage() method.
     */
    public CategoryExistsException(String message){
        super(message);
    }
}