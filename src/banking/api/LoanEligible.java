//Zeina

package banking.api;

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
