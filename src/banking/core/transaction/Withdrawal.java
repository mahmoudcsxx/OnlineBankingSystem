package banking.core.transaction;

import banking.common.TransactionType;
import banking.core.account.Account;

public class Withdrawal extends Transaction {

    private final Account sourceAccount;

    // Constructor
    public Withdrawal(Account sourceAccount, double amount) {
        super(null, amount, TransactionType.WITHDRAWAL, sourceAccount);      //transactionId (removed replaced with null)
        this.sourceAccount = sourceAccount;
    }

    @Override
    public void execute() {
        sourceAccount.withdraw(amount);
        setStatus("SUCCESS");

        System.out.println("Withdrawn " + amount +
                " from account " + sourceAccount.getAccountNumber());
    }
}
