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
import java.time.LocalDateTime;//gets the current date and time
import java.util.UUID;// generate a unique random ID

public abstract class Transaction {
    protected String transactionId;
    protected double amount;
    protected LocalDateTime date;
    protected TransactionType type;
    protected String status;
    protected Account sourceAccount;

    public Transaction(String transactionId, double amount, TransactionType type, Account sourceAccount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than 0");

        this.transactionId = UUID.randomUUID().toString();
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.type = type;
        this.status = "PENDING";
        this.sourceAccount = sourceAccount;
    }

    public abstract void execute();

    // FIX: renamed getTransaction() -> getTransactionId() for consistency; kept getTransaction() as alias for FileManager
    public String getTransactionId() { return transactionId; }
    public String getTransaction()   { return transactionId; }  // kept for FileManager compatibility

    public double getAmount()            { return amount; }
    public LocalDateTime getDate()       { return date; }
    public TransactionType getType()     { return type; }
    public String getStatus()            { return status; }
    public Account getAccount()          { return sourceAccount; }
    protected void setStatus(String s)   { this.status = s; }
    @Override
    public String toString() {
        return "Transaction{id='" + transactionId + "', amount=" + amount +
                ", date=" + date + ", type=" + type + ", status='" + status + "'}";
    }
}
