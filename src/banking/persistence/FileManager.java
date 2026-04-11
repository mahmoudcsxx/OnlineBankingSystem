/**
 * Online Banking System
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2 
 * @author Zainab Sabit (257156) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */

package banking.persistence;

import banking.common.AccountStatus;
import banking.core.account.Account;
import banking.core.account.SavingsAccount;
import banking.core.transaction.Deposit;
import banking.core.transaction.Transaction;
import banking.core.transaction.TransactionHistory;
import banking.core.user.StandardClient;
import banking.core.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileManager handles saving and loading users, accounts, and transactions to/from text files.
 * It uses simple comma-separated (.txt) files to persist banking data.
 */
public class FileManager {

    /** The base file path used to build file names (e.g., "data/bank" → "data/bank_users.txt") */
    private String filePath;

    /**
     * Constructor — sets the base file path for all file operations.
     * @param filePath the base path prefix for all generated files
     */
    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    // ===================== SAVE METHODS ===================== //

    /**
     * Saves all users to a text file (_users.txt).
     * Each line format: userId,name,password,STANDARD
     * @param users the list of users to save
     */
    public void saveUsers(ArrayList<User> users) {
        try (PrintWriter writer = new PrintWriter(filePath + "_users.txt")) {
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                // Write each user's details as a comma-separated line
                writer.println(u.getUserId() + "," + u.getName() + "," + u.getPassword() + ",STANDARD");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Saves all accounts to a text file (_accounts.txt).
     * Each line format: accountNumber,balance,status
     * @param accounts the list of accounts to save
     */
    public void saveAccounts(ArrayList<Account> accounts) {
        try (PrintWriter writer = new PrintWriter(filePath + "_accounts.txt")) {
            for (int i = 0; i < accounts.size(); i++) {
                Account a = accounts.get(i);
                // Write each account's number, balance, and status
                writer.println(a.getAccountNumber() + "," + a.getBalance() + "," + a.getStatus());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    /**
     * Saves all transactions to a text file (_transactions.txt).
     * Each line format: transactionId,amount,type,accountNumber
     * Only Deposit transactions include a linked account number.
     * @param transactions the list of transactions to save
     */
    public void saveTransactions(ArrayList<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(filePath + "_transactions.txt")) {
            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);

                // Only Deposit transactions have an associated account to retrieve
                String accountNumber = "";
                if (t instanceof Deposit) {
                    accountNumber = ((Deposit) t).getAccount().getAccountNumber();
                }

                // Write transaction details as a comma-separated line
                writer.println(t.getTransactionId() + "," + t.getAmount() + "," + t.getType() + "," + accountNumber);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // ===================== LOAD METHODS ===================== //

    /**
     * Loads all users from the _users.txt file.
     * Reconstructs each user as a StandardClient with placeholder phone/email values.
     * @return list of loaded User objects
     */
    public ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath + "_users.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Ensure the line has at least userId, name, and password
                if (parts.length >= 3) {
                    // Reconstruct user as StandardClient (phone and email use placeholders)
                    User u = new StandardClient(parts[0], parts[1], parts[2],
                            "phone", "email", 0);
                    users.add(u);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Loads all accounts from the _accounts.txt file.
     * Reconstructs each account as a SavingsAccount with no owner assigned.
     * @param users the list of users (reserved for future owner-linking logic)
     * @return list of loaded Account objects
     */
    public ArrayList<Account> loadAccounts(ArrayList<User> users) {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath + "_accounts.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Ensure the line has at least accountNumber, balance, and status
                if (parts.length >= 3) {
                    // Reconstruct account as SavingsAccount (owner is null until linked)
                    Account a = new SavingsAccount(parts[0], Double.parseDouble(parts[1]), null,
                            AccountStatus.valueOf(parts[2]), new TransactionHistory(), 0.0);
                    accounts.add(a);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;
    }

    /**
     * Loads all transactions from the _transactions.txt file.
     * Matches each transaction to its account using the account number in the file.
     * Only reconstructs Deposit transactions.
     * @param accounts the list of accounts used to match transactions to their account
     * @return list of loaded Transaction objects
     */
    public ArrayList<Transaction> loadTransactions(ArrayList<Account> accounts) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath + "_transactions.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Search for the account that matches the account number in the file (parts[3])
                Account acc = null;
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountNumber().equals(parts[3])) {
                        acc = accounts.get(i);
                        break; // Stop once the matching account is found
                    }
                }

                // Only create the transaction if a matching account was found
                if (acc != null && parts.length >= 4) {
                    // Reconstruct transaction as a Deposit using the matched account and amount
                    Transaction t = new Deposit(acc, Double.parseDouble(parts[1]));
                    transactions.add(t);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }

    // ===================== UTILITY METHODS ===================== //

    /**
     * Appends a single line of text to the file.
     * Useful for logging or writing individual records incrementally.
     * @param data the string to write as a new line
     */
    public void writeLine(String data) {
        try {
            // FileWriter with 'true' enables append mode (does not overwrite existing content)
            PrintWriter writer = new PrintWriter(new FileWriter(filePath, true));
            writer.println(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing line: " + e.getMessage());
        }
    }

    /**
     * Reads all lines from the file and returns them as a list of strings.
     * Useful for reading raw file content line by line.
     * @return list of all lines read from the file
     */
    public ArrayList<String> readLines() {
        ArrayList<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));

            // Read each line and add it to the list
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading lines: " + e.getMessage());
        }
        return lines;
    }

    //  GETTERS & SETTERS  //

    /**
     * Returns the current base file path.
     * @return the file path string
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Updates the base file path.
     * @param filePath the new file path to use
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}