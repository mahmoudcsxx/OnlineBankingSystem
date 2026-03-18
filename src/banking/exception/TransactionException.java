package banking.exception;

public class TransactionException extends Exception {

    // Default constructor
    public TransactionException() {
        super("A transaction error occurred.");
    }

    // Constructor with custom message
    public TransactionException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}