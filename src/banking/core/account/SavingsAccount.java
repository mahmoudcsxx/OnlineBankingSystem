/**
 * Online Banking System 
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2
 * @author Youssef Hassan (250498) — Group A-12
 * @version 1.0
 * @since 19-3-2026
 */

package banking.core.account;

import banking.core.user.Client;
import banking.common.AccountStatus;
import banking.core.transaction.TransactionHistory;

/**
 * A Savings account that inherits its features from its abstract class
 */
public class SavingsAccount extends Account {

    private final double interestRate;

    public SavingsAccount(String accountNumber, double balance, Client owner,
                          AccountStatus status, TransactionHistory transactionHistory, double interestRate) {
        super(accountNumber, balance, owner, status, transactionHistory);
        this.interestRate = interestRate;
    }

    /**
     * Applies interest on the account
     */
    public void applyInterest() {
        if (interestRate <= 0) return;
        double interest = balance * interestRate;
        balance += interest;
    }

    /**
     * @return  the rate of the interest
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * @return The toString function overridden (Added more information into it + the main information)
     */
    @Override
    public String toString() {
        return "SavingsAccount --> interestRate: "  + interestRate + " / " + super.toString();
    }
}
