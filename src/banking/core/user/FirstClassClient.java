/**
 * Online Banking System 
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2
 * @author Mahmoud Samir (257678) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */

package banking.core.user;

import banking.api.Insurable;
import banking.api.LoanEligible;
import banking.common.ClientType;

/**
 * Represents a first-class tier client with loan and insurance privileges.
 */
public class FirstClassClient extends Client implements LoanEligible, Insurable {
    /**
     * Priority level for service ordering (lower = higher priority).
     */
    private int priorityLevel;
    /**
     * Max amount the client can borrow.
     */
    private double loanLimit;
    /**
     * Insurance coverage amount.
     */
    private double insuranceAmount;
    /**
     * Default constructor: sets priority level to 1, loan limit to $100,000, and insurance to $25,000.
     */
    public FirstClassClient() {
        super();
        this.clientType = ClientType.FIRST_CLASS;
        this.priorityLevel = 1;
        this.loanLimit = 100000;
        this.insuranceAmount = 25000;
    }
    /**
     * Full constructor: initializes all client details with custom priority, loan, and insurance values.
     */
    public FirstClassClient(String userId, String name, String email, String password,
                            String phoneNumber, int priorityLevel,
                            double loanLimit, double insuranceAmount) {
        super(userId, name, email, password, phoneNumber, ClientType.FIRST_CLASS);
        this.priorityLevel = priorityLevel;
        this.loanLimit = loanLimit;
        this.insuranceAmount = insuranceAmount;
    }
    /**
     * Approves the loan if the amount is positive and within the limit, otherwise denies it.
     * @return true if approved, false otherwise
     */
    @Override
    public boolean applyForLoan(double amount) {
        if (amount > 0 && amount <= loanLimit) {
            System.out.println("Loan approved for First Class Client: " + amount);
            return true;
        }
        System.out.println("Loan denied for First Class Client.");
        return false;
    }
    /**
     * Returns the loan limit.
     */
    @Override
    public double getLoanLimit() {
        return loanLimit;
    }
    /**
     * Updates the loan limit.
     */
    public void setLoanLimit(double loanLimit) {
        this.loanLimit = loanLimit;
    }
    /**
     * Returns the insurance coverage amount.
     */
    @Override
    public double getInsurance() {
        return insuranceAmount;
    }
    /**
     * Updates the insurance amount.
     */
    public void setInsuranceAmount(double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }
    /**
     * Processes an insurance claim for this client.
     */
    @Override
    public void claimInsurance() {
        System.out.println("Insurance claimed successfully for First Class Client.");
    }
    /**
     * Returns the service priority level of this client.
     *
     * @return the priority level as an {@code int}
     */
    public int getPriorityLevel() {
        return priorityLevel;
    }
    /**
     * Updates the service priority level for this client.
     *
     * @param priorityLevel the new priority level (lower value = higher priority)
     */
    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    /**
     * Returns a formatted summary of this client's key details.
     *
     * @return a string containing the client's ID, name, email, phone,
     *         priority level, loan limit, insurance amount, and account count
     */
    @Override
    public String toString() {
        return String.format(
                "FirstClassClient{userId='%s', name='%s', email='%s', phone='%s', priorityLevel=%d, loanLimit=%.2f, insuranceAmount=%.2f, accounts=%d}",
                userId, name, email, phoneNumber, priorityLevel, loanLimit, insuranceAmount, accounts.size()
        );
    }
}