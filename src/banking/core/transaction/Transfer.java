/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Ibrahim Mostafa (257511) — Group A-8
 * @version 1.0
 * @since 19-3-2026
 */

package banking.core.transaction;

import banking.common.TransactionType;
import banking.core.account.Account;

public class Transfer extends Transaction {
    private Account destinationAccount;

    public Transfer(String transactionId, double amount,
                    Account sourceAccount, Account destinationAccount) {
        super(transactionId, amount, TransactionType.TRANSFER, sourceAccount);
        this.destinationAccount = destinationAccount;
    }

    public Account getDestinationAccount()                  { return destinationAccount; }
    public void setDestinationAccount(Account dest)         { this.destinationAccount = dest; }

    @Override
    public void execute() {
        // FIX: use inherited sourceAccount (not a shadowed field)
        if (sourceAccount == null || destinationAccount == null) {
            System.out.println("Transfer failed: account is null");
            return;
        }
        sourceAccount.transfer(destinationAccount, amount);
        setStatus("SUCCESS");
    }

    @Override
    public String toString() {
        return "Transfer{id='" + transactionId + "', amount=" + amount +
                ", from=" + sourceAccount.getAccountNumber() +
                ", to=" + destinationAccount.getAccountNumber() + '}';
    }
}
