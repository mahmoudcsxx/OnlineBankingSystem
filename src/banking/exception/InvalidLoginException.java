/**
 * Online Banking System
 * Course: Programming in Java (25CSC104C)
 * British University in Egypt (BUE)
 *
 * Phase 1:
 *  @author Zainab Sabit (257156) – Group A-14
 *  @version 1.0
 *  @since 19-3-2026
 *
 * Phase 2:
 *  @author: Youssef Hassan (250498) – Group A-14
 *  @version: 2.0
 *  @since: 11-4-2026
 */

package banking.exception;


/** Thrown when login credentials are invalid */
public class InvalidLoginException extends BankException{
    public InvalidLoginException (String message){
        super(message);
    }
}
