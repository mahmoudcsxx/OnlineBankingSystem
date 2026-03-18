//package banking.core.transaction;
//
//import banking.common.TransactionType;
//import banking.core.account.Account;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//public abstract class Transaction {
//
//    // Attributes
//    protected String transactionId;
//    protected double amount;
//    protected LocalDateTime date;
//    protected TransactionType type;
//    protected String status;
//    // FIXED: add missing field
//    protected Account sourceAccount;
//
//    // Constructor
//    public Transaction(String transactionId, double amount, TransactionType type, Account sourceAccount) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Amount must be greater than 0");
//        }
//
//        this.transactionId = UUID.randomUUID().toString();
//        this.amount = amount;
//        this.date = LocalDateTime.now();
//        this.type = type;
//        this.status = "PENDING";
//        // FIXED: assign sourceAccount
//        this.sourceAccount = sourceAccount;
//    }
//
//    // Abstract method
//    public abstract void execute();
//
//    // Getters
//    // FIXED: getTransaction() → getTransactionId()
//    public String getTransactionId() {
//        return transactionId;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public TransactionType getType() {
//        return type;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    // FIXED: add missing getter for sourceAccount
//    public Account getAccount() {
//        return sourceAccount;
//    }
//
//    protected void setStatus(String status) {
//        this.status = status;
//    }
//
//    @Override
//    public String toString() {
//        return "Transaction{" +
//                "id='" + transactionId + '\'' +
//                ", amount=" + amount +
//                ", date=" + date +
//                ", type=" + type +
//                ", status='" + status + '\'' +
//                '}';
//    }
//}




//the old code://

package banking.core.transaction;

import banking.common.TransactionType;
import banking.core.account.Account;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Transaction {

    private final Account sourceAccount;
    // Attributes
    protected String transactionId;
    protected double amount;
    protected LocalDateTime date;
    protected TransactionType type;
    protected String status;

    // Constructor
    public Transaction(String transactionId, double amount, TransactionType type, Account sourceAccount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        this.transactionId = UUID.randomUUID().toString();
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.type = type;
        this.status = "PENDING"; // default
        this.sourceAccount = sourceAccount;
    }




    // Abstract method

    public abstract void execute();

    // Getters
    public String getTransaction() {
        return transactionId;
    }

//    public String getTransactionId()
//    {
//        return transactionId;
//    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Account getAccount() {
       return sourceAccount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + transactionId + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", type=" + type +
                ", status='" + status + '\'' +
                '}';
    }
}



