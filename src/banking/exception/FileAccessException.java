/**
 * Online Banking System
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * Phase 2:
 *  @author Malak Waleed (252304) — Group A-14
 *  @version 2.0
 *  @since 11-4-2026
 */

package banking.exception;

/** Thrown when reading/writing a data file fails */
public class FileAccessException extends BankException {
    public FileAccessException(String message) {
        super(message);
    }

    public FileAccessException(String message, Throwable cause) {
        super(message + " (" + cause.getMessage() + ")");
    }
}
