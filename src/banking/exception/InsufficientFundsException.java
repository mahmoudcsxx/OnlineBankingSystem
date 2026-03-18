package banking.exception;

/**
 * InsufficientFundsException is thrown when a user tries to withdraw or transfer
 * more money than is available in their account.
 */
public class InsufficientFundsException extends Exception {

    /**
     * Constructor for InsufficientFundsException
     * message it is The error message describing the insufficient funds situation
     */
    public InsufficientFundsException(String message) {
        super(message); // Pass the message to the BankException base class
    }
}