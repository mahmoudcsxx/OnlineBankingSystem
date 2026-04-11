/**
 * Online Banking System 
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2
 * @author Malak Waleed (252304) — Group A-7
 * @version 1.0
 * @since 19-3-2026
 */

package banking.core.account;

import banking.common.AccountStatus;
import banking.core.user.Client;
import banking.core.transaction.TransactionHistory;

/**
 * Premium account with an insurance buffer that extends the effective withdrawal limit.
 */
public class PremiumAccount extends Account {

    private double insuranceLimit;

    public PremiumAccount(String accountNumber, double balance, Client owner,
                          AccountStatus status, TransactionHistory transactionHistory,
                          double insuranceLimit) {
        super(accountNumber, balance, owner, status, transactionHistory);
        this.insuranceLimit = insuranceLimit;
    }

    /**
     * Allows withdrawal up to balance + insuranceLimit.
     */
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (amount > balance + insuranceLimit) {
            throw new IllegalArgumentException("Withdrawal exceeds balance and insurance limit");
        }
        balance -= amount;
        if (transactionHistory != null) {
            transactionHistory.recordWithdrawal(this, amount);
        }
    }

    public double getInsuranceLimit() {
        return insuranceLimit;
    }

    public void setInsuranceLimit(double insuranceLimit) {
        this.insuranceLimit = insuranceLimit;
    }

    @Override
    public String toString() {
        return "PremiumAccount --> insuranceLimit: " + insuranceLimit + " / " + super.toString();
    }
}