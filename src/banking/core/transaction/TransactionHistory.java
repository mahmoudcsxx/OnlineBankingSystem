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

import banking.core.account.Account;

import java.util.ArrayList;

public class TransactionHistory {

    // Attributes
    private final ArrayList<Transaction> transactions;

    // Constructor
    public TransactionHistory() {
        transactions = new ArrayList<>();
    }

    // addTransaction(t: Transaction): void
    public void addTransaction(Transaction t) {
        if (t == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        transactions.add(t);
    }

    // getHistory(): ArrayList<Transaction>
    public ArrayList<Transaction> getHistory() {
        return new ArrayList<>(transactions); // return copy (safe)
    }

    // displayHistory(): void
    public void displayHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // clearHistory(): void
    public void clearHistory() {
        transactions.clear();
    }
/**/
    public void recordWithdrawal(Account account, double amount) {
    }

    public void recordTransfer(Account account, Account destination, double amount) {
    }

    public void recordDeposit(Account account, double amount) {
    }
    /**/

}