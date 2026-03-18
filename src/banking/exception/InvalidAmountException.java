package banking.exception;

/**
 * InvalidAmountException is thrown when a user tries to deposit, withdraw,
 * or transfer an invalid amount (e.g., negative or zero).
 */
public class InvalidAmountException extends Exception {

    /**
     * Constructor for InvalidAmountException
     *  message The error message describing the invalid amount
     */
    public InvalidAmountException(String message) {
        super(message); // Pass message to the base BankException
    }
}