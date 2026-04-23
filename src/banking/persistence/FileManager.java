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

import banking.common.AccountType;
import banking.common.AccountStatus;
import banking.core.account.Account;
import banking.core.account.BusinessAccount;
import banking.core.account.CurrentAccount;
import banking.core.account.PremiumAccount;
import banking.core.account.SavingsAccount;
import banking.core.transaction.Deposit;
import banking.core.transaction.Transaction;
import banking.core.transaction.TransactionHistory;
import banking.core.user.Admin;
import banking.core.user.Client;
import banking.core.user.StandardClient;
import banking.core.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileManager handles saving and loading users, accounts, and transactions to/from CSV files.
 * It uses simple comma-separated (.csv) files to persist banking data.
 */
public class FileManager {

    /** The base file path used to build file names (e.g., "data/bank" → "data/bank_users.csv") */
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
     * Saves all users to a CSV file (_users.csv).
     * Each line format: userId,name,email,password,role[,phone]
     * @param users the list of users to save
     */
    public void saveUsers(ArrayList<User> users) {
        try (PrintWriter writer = new PrintWriter(filePath + "_users.csv")) {
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                String role = u instanceof Admin ? "ADMIN" : "STANDARD";
                String phone = u instanceof Client ? ((Client) u).getPhoneNumber() : "";
                writer.println(u.getUserId() + "," + u.getName() + "," + u.getEmail()
                        + "," + u.getPassword() + "," + role + "," + phone);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Saves all accounts to a CSV file (_accounts.csv).
     * Each line format: accountNumber,type,balance,status,ownerUserId,extra
     * @param accounts the list of accounts to save
     */
    public void saveAccounts(ArrayList<Account> accounts) {
        try (PrintWriter writer = new PrintWriter(filePath + "_accounts.csv")) {
            for (int i = 0; i < accounts.size(); i++) {
                Account a = accounts.get(i);
                String ownerUserId = a.getOwner() != null ? a.getOwner().getUserId() : "";
                writer.println(a.getAccountNumber() + "," + inferAccountType(a) + ","
                        + a.getBalance() + "," + a.getStatus() + ","
                        + ownerUserId + "," + extraValueFor(a));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    /**
     * Saves all transactions to a CSV file (_transactions.csv).
     * Each line format: transactionId,amount,type,accountNumber,status,date
     * @param transactions the list of transactions to save
     */
    public void saveTransactions(ArrayList<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(filePath + "_transactions.csv")) {
            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);

                String accountNumber = "";
                if (t.getAccount() != null) {
                    accountNumber = t.getAccount().getAccountNumber();
                }

                writer.println(t.getTransactionId() + "," + t.getAmount() + ","
                        + t.getType() + "," + accountNumber + ","
                        + t.getStatus() + "," + t.getDate());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // ===================== LOAD METHODS ===================== //

    /**
     * Loads all users from the _users.csv file.
     * Reconstructs each user according to the saved role.
     * @return list of loaded User objects
     */
    public ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath + "_users.csv"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    String role = parts[4].trim();
                    if ("ADMIN".equalsIgnoreCase(role)) {
                        users.add(new Admin(parts[0], parts[1], parts[2], parts[3]));
                    } else {
                        String phone = parts.length >= 6 ? parts[5] : "";
                        users.add(new StandardClient(parts[0], parts[1], parts[2],
                                parts[3], phone, 1000.0));
                    }
                } else if (parts.length >= 4) {
                    String legacyRole = parts[3].trim();
                    if ("ADMIN".equalsIgnoreCase(legacyRole)) {
                        users.add(new Admin(parts[0], parts[1], parts[2], "phone"));
                    } else if ("STANDARD".equalsIgnoreCase(legacyRole)) {
                        users.add(new StandardClient(parts[0], parts[1], parts[2],
                                "phone", "", 1000.0));
                    } else {
                        users.add(new StandardClient(parts[0], parts[1], parts[2],
                                parts[3], "", 1000.0));
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Loads all accounts from the _accounts.csv file.
     * Reconstructs each account from CSV and links it to its owner when possible.
     * @param users the list of users used for owner-linking logic
     * @return list of loaded Account objects
     */
    public ArrayList<Account> loadAccounts(ArrayList<User> users) {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath + "_accounts.csv"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    Client owner = findClientById(users, parts[4]);
                    Account a = buildAccount(parts, owner);
                    accounts.add(a);
                    if (owner != null) {
                        owner.addAccount(a);
                    }
                } else if (parts.length >= 3) {
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
     * Loads all transactions from the _transactions.csv file.
     * Matches each transaction to its account using the account number in the file.
     * Only reconstructs Deposit transactions.
     * @param accounts the list of accounts used to match transactions to their account
     * @return list of loaded Transaction objects
     */
    public ArrayList<Transaction> loadTransactions(ArrayList<Account> accounts) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath + "_transactions.csv"));

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

    private Client findClientById(ArrayList<User> users, String userId) {
        if (userId == null || userId.isBlank()) {
            return null;
        }
        for (User user : users) {
            if (user instanceof Client && userId.equals(user.getUserId())) {
                return (Client) user;
            }
        }
        return null;
    }

    private Account buildAccount(String[] parts, Client owner) {
        String accountNumber = parts[0];
        AccountType accountType = AccountType.valueOf(parts[1]);
        double balance = Double.parseDouble(parts[2]);
        AccountStatus status = AccountStatus.valueOf(parts[3]);
        TransactionHistory history = new TransactionHistory();
        String extra = parts.length >= 6 ? parts[5] : "";

        switch (accountType) {
            case CURRENT:
                return new CurrentAccount(accountNumber, balance, owner, status, history,
                        extra.isBlank() ? 0.0 : Double.parseDouble(extra));
            case BUSINESS:
                return new BusinessAccount(accountNumber, balance, owner, status, history,
                        extra.isBlank() ? "Business" : extra);
            case SAVINGS:
            default:
                return new SavingsAccount(accountNumber, balance, owner, status, history,
                        extra.isBlank() ? 0.0 : Double.parseDouble(extra));
        }
    }

    private String inferAccountType(Account account) {
        if (account instanceof CurrentAccount) {
            return AccountType.CURRENT.name();
        }
        if (account instanceof BusinessAccount) {
            return AccountType.BUSINESS.name();
        }
        return AccountType.SAVINGS.name();
    }

    private String extraValueFor(Account account) {
        if (account instanceof SavingsAccount) {
            return String.valueOf(((SavingsAccount) account).getInterestRate());
        }
        if (account instanceof CurrentAccount) {
            return String.valueOf(((CurrentAccount) account).getOverdraftLimit());
        }
        if (account instanceof PremiumAccount) {
            return String.valueOf(((PremiumAccount) account).getInsuranceLimit());
        }
        if (account instanceof BusinessAccount) {
            return ((BusinessAccount) account).getBusinessName();
        }
        return "";
    }
}
