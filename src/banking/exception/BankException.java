/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 * @author Mahmoud Samir (257678) — Group A-14 , Youssef Hassan (250498) — Group A-12
 * @version 1.1
 * @since 8-4-2026
 * @author Zeina Alaaeldin (254588) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */

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
        super(message);
    }

    public BankException(String message, Throwable cause) {
        /**
         * Constructor with custom error message.
         * message it is Detailed description of the error.
         */
        super(message, cause); // Pass message to parent Exception class
    }
}
