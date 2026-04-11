/**
 * Online Banking System
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * Phase - 2
 *  @author Zeina Alaaeldin (254588) — Group A-14
 *  @version 1.0
 *  @since 19-3-2026
 *
 * Phase 2:
 *  @author Mahmoud Samir (257678) — Group A-14
 *  @version 2.0
 *  @since 10-4-2026
 */

package banking.exception;

/** Thrown when an account lookup fails  (no such account number in the system) */
public class AccountNotFoundException extends BankException{
    public AccountNotFoundException(String message){
        super(message);
    }
}