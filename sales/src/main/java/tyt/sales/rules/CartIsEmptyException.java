package tyt.sales.rules;

public class CartIsEmptyException extends RuntimeException{
    public CartIsEmptyException(String message) {
        super(message);
    }
}
