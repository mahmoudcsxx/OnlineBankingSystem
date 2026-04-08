/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Zainab Sabit (257156) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */

package banking.exception;

/**
 * InsufficientFundsException is thrown when a user tries to withdraw or transfer
 * more money than is available in their account.
 */
public class InsufficientFundsException extends BankException {

    /**
     * Constructor for InsufficientFundsException
     * @param message The error message describing the insufficient funds situation
     */
    public InsufficientFundsException(String message) {
        super(message); // Pass the message to the BankException base class
    }
}