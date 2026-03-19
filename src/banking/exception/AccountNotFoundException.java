/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Zeina Alaaeldin (254588) — Group A-14
 * @version 1.0
 * @since 19-3-2026
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
