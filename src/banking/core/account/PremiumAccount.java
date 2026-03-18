
package banking.core.account;

import banking.api.Insurable;
import banking.api.Transferable;
import banking.core.account.Account;
import banking.common.AccountStatus;
import banking.common.ClientType;
import banking.common.AccountType;
import banking.core.transaction.TransactionHistory;

public class PremiumAccount extends Account implements Transferable  {

    private double insuranceLimit;

    public PremiumAccount(String accountNumber, double balance) {
        super(accountNumber, balance, AccountType.PREMIUM);
        this.insuranceLimit = 10000.0;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }
        if (balance + insuranceLimit >= amount) {
            balance -= amount;
        } else {
            System.out.println("withdrawal exceeds allowed limit.");
        }
    }

    @Override
    public void transfer(Account destination, double amount) {
        if (destination == null) {
            System.out.println("invalid destination account.");
            return;
        }
        if (balance + insuranceLimit >= amount) {
            this.withdraw(amount);
            destination.deposit(amount);
        } else {
            System.out.println("Transfer failed: insufficient funds.");
        }


    }
    
}


