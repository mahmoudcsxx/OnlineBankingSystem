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
 * InvalidLoginException is thrown when a user tries to log in with invalid credentials.
 * Example: wrong username or password.
 */
public class InvalidLoginException extends BankException
{
    /**
     * Constructor for InvalidLoginException
     * @param message The error message describing the login failure
     */
    public InvalidLoginException(String message) {
        super(message); // Pass the message to the BankException base class
    }
}