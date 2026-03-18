package banking.core.account;

import banking.common.AccountStatus;
import banking.common.AccountType;
import banking.core.user.Client;
import banking.core.transaction.TransactionHistory;

// Abstract class (reusable blueprint for its subclasses {Savings, Current, Business} accounts)
public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected Client owner;
    protected AccountStatus status;
    protected TransactionHistory transactionHistory;

    // A new account is created with these details
    public Account(String accountNumber, double balance, Client owner, AccountStatus status, TransactionHistory transactionHistory) {

        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
        this.status = status;
        this.transactionHistory = transactionHistory;

    }

    // deposits an amount into the account
    // If an amount is less than or equal to 0, An exception is handled
    public void deposit(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        this.balance += amount;
        if(transactionHistory != null) {
            transactionHistory.recordDeposit(this, amount);
        }
    }

    // withdraws an amount from the account
    // If an amount is less than or equal to 0, An exception is handled
    public void withdraw(double amount) {
        if(amount <=0) { // The deposited amount should
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if(amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        this.balance -=amount;
        if(transactionHistory != null) {
            transactionHistory.recordWithdrawal(this, amount);
        }
    }

    // transfers an amount from his account to another account
    // if the destination is not specified, an exception is handled
    public void transfer(Account destination, double amount) {
        if(destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }

        this.withdraw(amount);
        destination.deposit(amount);

        if(transactionHistory != null) {
            transactionHistory.recordTransfer(this, destination, amount);
        }
    }

    // Returns the current balance of the account
    public double getBalance() {
        return balance;
    }

    // Returns the account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Returns the name of the account owner
    public Client getOwner() {
        return owner;
    }

    // Returns the current status of the account {ACTIVE, SUSPENDED, CLOSED}
    public AccountStatus getStatus() {
        return status;
    }

    // Sets the status of the account depending on its status
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    // Returns the transaction history associated with the account
    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    // The function to get overridden on the 3 classes, savings, current, and business
    public String toString() {
        return "Account --> AccountNumber: " + accountNumber + " / " + " balance: " + balance +
                " / "  + " Owner: " + owner + " / " + " Status: " + status + " / ";
    }
}
