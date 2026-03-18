package banking.core.account;

import banking.common.AccountStatus;
import banking.core.user.Client;
import banking.core.transaction.TransactionHistory;

/**
 * The current account of the owner that inherits its features from its abstract class
 */
public class CurrentAccount extends Account {

    private double overdraftLimit;
    public CurrentAccount(String accountNumber, double balance, Client owner,
                          AccountStatus status, TransactionHistory transactionHistory, double overdraftLimit) {
        super(accountNumber, balance, owner, status, transactionHistory);
        this.overdraftLimit = overdraftLimit;
    }

    /** Overridden function from the {Account} Class to add the overdraft limit exception
     * If the amount is greater than the balance and the overdraft limit combined, an exception is handled
     */

    @Override
    public void withdraw(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if(amount > (balance + overdraftLimit)) {
            throw new IllegalArgumentException("Overdraft limit exceeded");
        }
        balance -= amount;
        if(transactionHistory != null) {
            transactionHistory.recordWithdrawal(this, amount);
        }
    }

    /**
     * @return the overdraft limit of the account
     **/
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    /**
     * Sets the limit of the overdraft for the account
     */
    public void setOverdraftLimit(double limit) {
        this.overdraftLimit = limit;
    }

    /**
     * @return The toString function overridden (Added more information into it + the main information)
     */
    @Override
    public String toString() {
        return "CurrentAccount --> OverdraftLimit: " + overdraftLimit + " / " + super.toString();
    }

}
