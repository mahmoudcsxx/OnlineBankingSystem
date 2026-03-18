
package banking.exception;

/**
 * InvalidLoginException is thrown when a user tries to log in with invalid credentials.
 * Example: wrong username or password.
 */
public class InvalidLoginException extends Exception
{
    /**
     * Constructor for InvalidLoginException
     * @param message The error message describing the login failure
     */
    public InvalidLoginException(String message) {
        super(message); // Pass the message to the BankException base class
    }
}