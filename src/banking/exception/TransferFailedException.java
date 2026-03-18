package banking.exception;

/**
 * TransferFailedException is thrown when a money transfer cannot be completed.
 * Example: insufficient funds, invalid account, system error during transfer.
 */
public class TransferFailedException extends Exception {

    /**
     * Constructor for TransferFailedException
     *  message it is The error message describing the transfer failure
     */
    public TransferFailedException(String message) {
        super(message); // Pass the message to the BankException base class
    }
}