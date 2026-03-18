 //RuntimeException

package banking.exception;

/**
 * UserAlreadyExistsException is thrown when trying to register a user
 * that already exists in the system.
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * Constructor for UserAlreadyExistsException
     * message it is The error message describing the duplicate user
     */
    public UserAlreadyExistsException(String message) {
        super(message); // Pass the message to the BankException base class
    }
}