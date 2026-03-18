package banking.core.user;

import banking.api.Insurable;
import banking.api.LoanEligible;
import banking.common.ClientType;

/**
 * Represents a premium-tier client who can apply for loans and claim insurance.
 */
public class PremiumClient extends Client implements LoanEligible, Insurable {

    /** Max amount the client can borrow. */
    private double loanLimit;

    /** Insurance coverage amount. */
    private double insuranceAmount;

    /**
     * Default constructor. Sets loan limit to 50,000 and insurance to 10,000.
     */
    public PremiumClient() {
        super();
        this.clientType = ClientType.PREMIUM;
        this.loanLimit = 50000;
        this.insuranceAmount = 10000;
    }

    /**
     * Full constructor. Initializes all client details with custom loan and insurance values.
     *
     * @param userId          Unique identifier for the client.
     * @param name            Full name of the client.
     * @param email           Email address of the client.
     * @param password        Password for the client account.
     * @param phoneNumber     Contact phone number of the client.
     * @param loanLimit       Maximum amount the client can borrow.
     * @param insuranceAmount Insurance coverage amount.
     */
    public PremiumClient(String userId, String name, String email, String password,
                         String phoneNumber, double loanLimit, double insuranceAmount) {
        super(userId, name, email, password, phoneNumber, ClientType.PREMIUM);
        this.loanLimit = loanLimit;
        this.insuranceAmount = insuranceAmount;
    }

    /**
     * Approves the loan if the amount is positive and within the limit, otherwise denies it.
     *
     * @param amount The amount the client wishes to borrow.
     * @return true if approved, false otherwise.
     */
    @Override
    public boolean applyForLoan(double amount) {
        if (amount > 0 && amount <= loanLimit) {
            System.out.println("Loan approved for Premium Client: " + amount);
            return true;
        }
        System.out.println("Loan denied for Premium Client.");
        return false;
    }

    /**
     * Returns the client's loan limit.
     *
     * @return the maximum amount the client can borrow.
     */
    @Override
    public double getLoanLimit() {
        return loanLimit;
    }

    /**
     * Updates the loan limit.
     *
     * @param loanLimit The new loan limit to set.
     */
    public void setLoanLimit(double loanLimit) {
        this.loanLimit = loanLimit;
    }

    /**
     * Returns the insurance coverage amount.
     *
     * @return the insurance amount.
     */
    @Override
    public double getInsurance() {
        return insuranceAmount;
    }

    /**
     * Updates the insurance amount.
     *
     * @param insuranceAmount The new insurance amount to set.
     */
    public void setInsuranceAmount(double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    /**
     * Processes an insurance claim for this client.
     */
    @Override
    public void claimInsurance() {
        System.out.println("Insurance claimed successfully for Premium Client.");
    }

    /**
     * Returns a formatted summary of this client's key details.
     *
     * @return a string containing the client's ID, name, email, phone,
     *         loan limit, insurance amount, and account count.
     */
    @Override
    public String toString() {
        return String.format(
                "PremiumClient{userId='%s', name='%s', email='%s', phone='%s', loanLimit=%.2f, insuranceAmount=%.2f, accounts=%d}",
                userId, name, email, phoneNumber, loanLimit, insuranceAmount, accounts.size()
        );
    }
}