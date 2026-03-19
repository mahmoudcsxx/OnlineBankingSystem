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