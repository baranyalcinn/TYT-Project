package tyt.product.exception;

/**
 * This class represents a custom exception that is thrown when a category does not exist in the system.
 * It extends the RuntimeException class, meaning it's an unchecked exception.
 */
public class NoSuchCategoryException extends RuntimeException{

    /**
     * Constructs a new NoSuchCategoryException with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to Throwable.initCause(java.lang.Throwable).
     *
     * @param message the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public NoSuchCategoryException(String message){
        super(message);
    }
}