/**
 * Online Banking System
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2
 * @author Zainab Sabit (257156) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */

package banking.exception;

/**
 * TransferFailedException is thrown when a money transfer cannot be completed.
 * Example: insufficient funds, invalid account, system error during transfer.
 */
public class TransferFailedException extends BankException {

    /**
     * Constructor for TransferFailedException
     *  @param message The error message describing the transfer failure
     */
    public TransferFailedException(String message) {
        super(message); // Pass the message to the BankException base class
    }
}