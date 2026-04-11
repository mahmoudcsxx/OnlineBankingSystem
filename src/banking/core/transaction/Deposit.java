/**
 * Online Banking System 
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2
 * @author Ibrahim Mostafa (257511) — Group A-8
 * @version 1.0
 * @since 19-3-2026
 */

package banking.core.transaction;

import banking.common.TransactionType;
import banking.core.account.Account;

public class Deposit extends Transaction {

    private final Account targetAccount;

    // Constructor
    // FIXED: removed duplicate sourceAccount field (already in Transaction)
    // FIXED: generate transactionId with UUID.randomUUID() directly in super()
    // FIXED: deposit has no sourceAccount so pass null
    public Deposit(Account targetAccount, double amount) {
        super(null,
                amount,
                TransactionType.DEPOSIT,
                null);
        this.targetAccount = targetAccount;
    }

    @Override
    public void execute() {
        targetAccount.deposit(amount);
        setStatus("SUCCESS");
        System.out.println("Deposited " + amount +
                " to account " + targetAccount.getAccountNumber());
    }
}


//#ErrorFix1Cannot reference 'transactionId' before super()Pass null — UUID is already generated inside Transaction constructor
// 2Cannot reference 'sourceAccount' before super()Pass null — deposit has no source account
// 3Private 'sourceAccount' never assignedRemoved the duplicate field — it already exists in Transaction parent class