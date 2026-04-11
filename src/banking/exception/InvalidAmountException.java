/**
 * Online Banking System
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * Phase - 2
 *  @author Malak Waleed (252304) — Group A-7
 *  @version 1.0
 *  @since 19-3-2026
 *
 * Phase 2:
 *  @author Ibrahim Mostafa (257511) — Group A-8
 *  @version 2.0
 *  @since 10-4-2026
 */

package banking.exception;

/** Thrown when a deposit/withdraw/transfer amount is invalid (non-numeric, zero, negative) */
public class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
