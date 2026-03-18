//Zeina

/**
 * This exception is thrown when an operation tries to access an account
 * that does not exist in the system.
 */
package banking.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        /**
         * Default constructor.
         * Creates an exception without a custom message.
         */
        super(); // Call the parent Exception constructor
    }

    public AccountNotFoundException(String message) {
        /**
         * Constructor with a detailed error message.
         * @param message description of the error (ex: which account was not found)
         */
        super(message); // Pass message to parent Exception class
    }
}
