//Zeina

/**
 * This is the base class for all exceptions in the banking system.
 * -> Any error or unusual situation that occurs during bank operations
 * (like invalid transactions, insufficient funds, account issues) can
 * be represented by a subclass of this exception.
 *
 */
package banking.exception;

public class BankException extends RuntimeException {

    public BankException() {
        /**
         * Default constructor.
         * Allows creating a BankException without a message.
         */
        super(); // Call the parent Exception constructor
    }

    public BankException(String message) {
        /**
         * Constructor with custom error message.
         * message it is Detailed description of the error.
         */
        super(message); // Pass message to parent Exception class
    }
}
