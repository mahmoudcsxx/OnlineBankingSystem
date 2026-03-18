package banking.core.user;

import banking.api.Insurable;
import banking.api.LoanEligible;
import banking.common.ClientType;

// Represents a premium-tier client who can apply for loans and claim insurance
public class PremiumClient extends Client implements LoanEligible, Insurable{
    private double loanLimit; // Max amount the client can borrow
    private double insuranceAmount; // Insurance coverage amount

    // Default constructor: sets loan limit to 50,000 and insurance to 10,000
    public PremiumClient(){
        super();
        this.clientType = ClientType.PREMIUM;
        this.loanLimit = 50000;
        this.insuranceAmount = 10000;
    }
    // Full constructor: initializes all client details with custom loan and insurance values
    public PremiumClient(String userId, String name, String email, String password,
                          String phoneNumber, double loanLimit, double insuranceAmount) {
        super(userId, name, email, password, phoneNumber, ClientType.PREMIUM);
        this.loanLimit = loanLimit;
        this.insuranceAmount = insuranceAmount;
    }
    // Approves the loan if amount is positive and within the limit, otherwise denies it
    @Override
    public boolean applyForLoan(double amount) {
        if (amount > 0 && amount <= loanLimit) {
            System.out.println("Loan approved for Premium Client: " + amount);
            return true;
        }
        System.out.println("Loan denied for Premium Client.");
        return false;
    }
    // Returns the client's loan limit
    @Override
    public double getLoanLimit() {
        return loanLimit;
    }
    // Sets the loan limit
    public void setLoanLimit(double loanLimit) {
        this.loanLimit = loanLimit;
    }
    // Returns the insurance coverage amount
    @Override
    public double getInsurance() {
        return insuranceAmount;
    }
    // Sets the insurance amount
    public void setInsuranceAmount(double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }
    // Processes an insurance claim for this client
    @Override
    public void claimInsurance() {
        System.out.println("Insurance claimed successfully for Premium Client.");
    }
    // Returns a summary string with the client's key details
    @Override
    public String toString() {
        return String.format(
                "PremiumClient{userId='%s', name='%s', email='%s', phone='%s', loanLimit=%.2f, insuranceAmount=%.2f, accounts=%d}",
                userId, name, email, phoneNumber, loanLimit, insuranceAmount, accounts.size()
        );
    }
}


