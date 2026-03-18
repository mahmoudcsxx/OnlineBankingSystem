package banking.core.transaction;
import banking.common.TransactionType;
import banking.core.account.Account;
import banking.core.transaction.Transaction;

public class Transfer extends Transaction {
    private Account sourceAccount;

    private Account destinationAccount;

    // Constructor (matches UML: Transfer(...))
    public Transfer(String transactionId, double amount,
                    Account sourceAccount, Account destinationAccount) {
        super(transactionId,
                amount,
                TransactionType.TRANSFER,
                sourceAccount);
        this.destinationAccount = destinationAccount;
    }

    // Getter
    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    @Override
    public void execute() {
        if (sourceAccount == null || destinationAccount == null) {
            System.out.println("Transfer failed: account is null");
            return;
        }

        sourceAccount.transfer(destinationAccount, amount);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id='" + transactionId + '\'' +
                ", amount=" + amount +
                ", from=" + sourceAccount.getAccountNumber() +
                ", to=" + destinationAccount.getAccountNumber() +
                '}';
    }
}