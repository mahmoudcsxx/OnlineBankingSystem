/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Zainab Sabit (257156) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */

//RuntimeException

package banking.exception;

/**
 * UserAlreadyExistsException is thrown when trying to register a user
 * that already exists in the system.
 */
public class UserAlreadyExistsException extends BankException {

    /**
     * Constructor for UserAlreadyExistsException
     * message it is The error message describing the duplicate user
     */
    public UserAlreadyExistsException(String message) {
        super(message); // Pass the message to the BankException base class
    }
}