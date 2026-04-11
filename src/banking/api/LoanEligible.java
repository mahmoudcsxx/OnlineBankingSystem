/**
 * Online Banking System 
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2
 * @author Zeina Alaaeldin (254588) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */
package banking.api;

/**
 * This interface represents the ability of a client to apply for a loan.
 * In the banking system, not every client is allowed to take loans.
 * Only specific client types such as PremiumClient and FirstClassClient
 * will implement this interface
 */

public interface LoanEligible {
    /**
     * @param amount the amount of money the client wants to borrow.
     * @return true if the loan is approved, false otherwise.
     */
    boolean applyForLoan(double amount);
    /**
     * Returns the loan's limit amount the client is allowed to request.
     */
    double getLoanLimit();
}
