/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 * @author Mahmoud Samir (257678) — Group A-14 , Youssef Hassan (250498) — Group A-12
 * @version 1.1
 * @since 8-4-2026
 * @author Malak Waleed (252304) — Group A-7
 * @version 1.0
 * @since 19-3-2026
 */

package banking.exception;

/**
 * InvalidAmountException is thrown when a user tries to deposit, withdraw,
 * or transfer an invalid amount (e.g., negative or zero).
 */
public class InvalidAmountException extends BankException {

    /**
     * Constructor for InvalidAmountException
     *  message The error message describing the invalid amount
     */
    public InvalidAmountException(String message) {

        super(message); // Pass message to the base BankException
    }
}