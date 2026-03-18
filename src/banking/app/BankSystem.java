package banking.app;

import banking.common.AccountStatus;
import banking.core.account.*;
import banking.core.transaction.TransactionHistory;
import banking.core.user.*;
import banking.core.transaction.Transaction;
import banking.exception.InsufficientFundsException;
import banking.persistence.FileManager;
import banking.service.TransactionManager;

import java.util.ArrayList;
import java.util.Scanner;

public class BankSystem {
}
/**
 *
 *
 *
 *
 *
 *
 *
 */

/*

package banking.app;

import banking.core.user.User;
import banking.core.account.Account;
import banking.core.transaction.Transaction;
import banking.service.TransactionManager;
import banking.presentation.TransactionView;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;

public class BankSystem

{
    //Attributes
    private ArrayList<User> users;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private TransactionManager transactionManager;
    private TransactionView transactionView;

    //Constructor
    public BankSystem()
    {
        users = new ArrayList<>();
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        transactionManager = new TransactionManager();
        transactionView = new TransactionView();
    }

    // Main Method
    public static void main(String[] args)
    {
        BankSystem system = new BankSystem();
        system.startSystem();
    }

    // Start System
    public void startSystem() {
        System.out.println("Bank System Started...");
        loadFromFile();
    }

    // load data from files
    public void loadFromFile()
    {
        System.out.println("Loading data from file...");
    }

    // Create User
    public void createUser(User user)
    {
        users.add(user);
        System.out.println("User created successfully.");
    }

    // Create Account
    public void createAccount(Account account)
    {
        accounts.add(account);
        System.out.println("Account created successfully.");
    }

    // Find User by ID
    public User findUserById(String userId)
    {
        for (User user : users)
        {
            if (user.getUserId().equals(userId))
            {
                return user;
            }
        }
        return null;
    }

    // Save Data to File
    public Account findAccountByNumber(String accountNumber)
    {
        for (Account account : accounts)
        {
            if (account.getAccountNumber().equals(accountNumber))
            {
                return account;
            }
        }
        return null;
    }

    // Save Data to File
    public void saveToFile()
    {
        System.out.println("Saving data to file...");
    }

    // Display All Users
    public void displayAllUsers()
    {
        for (User user : users)
        {
            System.out.println(user);
        }
    }

    // Display All Accounts
    public void displayAllAccounts()
    {
        for (Account account : accounts)
        {
            System.out.println(account);
        }
    }
}
*/
/*


public class BankSystem {

    // ── Data ─────────────────────────────────────────────────────────────────
    private ArrayList<User>        users;
    private ArrayList<Account>     accounts;
    private ArrayList<Transaction> transactions;

    // ── Services ──────────────────────────────────────────────────────────────
    private final TransactionManager transactionManager;
    private final FileManager        fileManager;
    private final Scanner            scanner;

    // ── Session ───────────────────────────────────────────────────────────────
    private User loggedInUser;

    private static final String DATA_PATH = "bank_data";

    // ══════════════════════════════════════════════════════════════════════════
    public BankSystem() {
        users              = new ArrayList<>();
        accounts           = new ArrayList<>();
        transactions       = new ArrayList<>();
        transactionManager = new TransactionManager();
        fileManager        = new FileManager(DATA_PATH);
        scanner            = new Scanner(System.in);
        loggedInUser       = null;
    }

    public static void main(String[] args) {
        BankSystem system = new BankSystem();
        system.startSystem();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  STARTUP
    // ══════════════════════════════════════════════════════════════════════════
    public void startSystem() {
        printBanner();
        loadFromFile();
        seedAdminIfEmpty();
        mainLoop();
        saveToFile();
        System.out.println("\nGoodbye!");
    }

    private void seedAdminIfEmpty() {
        for (User u : users) if (u instanceof Admin) return;
        users.add(new Admin("ADMIN001", "Admin", "admin@bank.com", "admin123"));
        System.out.println("[INFO] Default admin created -> email: admin@bank.com | password: admin123");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  FILE PERSISTENCE  (fully wired)
    // ══════════════════════════════════════════════════════════════════════════
    public void loadFromFile() {
        users        = fileManager.loadUsers();
        accounts     = fileManager.loadAccounts(users);   // re-links accounts to their owners
        transactions = fileManager.loadTransactions(accounts);
        System.out.println("[INFO] Loaded " + users.size() + " users, "
                + accounts.size() + " accounts, "
                + transactions.size() + " transactions.");
    }

    public void saveToFile() {
        fileManager.saveUsers(users);
        fileManager.saveAccounts(accounts);
        fileManager.saveTransactions(transactions);
        System.out.println("[INFO] Data saved.");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN LOOP
    // ══════════════════════════════════════════════════════════════════════════
    private void mainLoop() {
        while (true) {
            if (loggedInUser == null) {
                int choice = guestMenu();
                if      (choice == 1) login();
                else if (choice == 2) registerClient();
                else if (choice == 0) break;
                else System.out.println("Invalid option.");
            } else if (loggedInUser instanceof Admin) {
                int choice = adminMenu();
                switch (choice) {
                    case 1: registerClient();  break;
                    case 2: viewAllUsers();    break;
                    case 3: viewAllAccounts(); break;
                    case 4: removeUserFlow();  break;
                    case 5: saveToFile();      break;
                    case 0: logout();          break;
                    default: System.out.println("Invalid option.");
                }
            } else {
                // logged in as a Client
                int choice = clientMenu();
                switch (choice) {
                    case 1: openAccountFlow();   break;
                    case 2: depositFlow();       break;
                    case 3: withdrawFlow();      break;
                    case 4: transferFlow();      break;
                    case 5: viewBalanceFlow();   break;
                    case 6: viewHistoryFlow();   break;
                    case 7: myAccountsFlow();    break;
                    case 0: logout();            break;
                    default: System.out.println("Invalid option.");
                }
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MENUS
    // ══════════════════════════════════════════════════════════════════════════
    private void printBanner() {
        System.out.println("==========================================");
        System.out.println("        WELCOME TO THE BANK SYSTEM        ");
        System.out.println("==========================================");
    }

    private int guestMenu() {
        System.out.println("\n-------- MAIN MENU --------");
        System.out.println("  1. Login");
        System.out.println("  2. Register");
        System.out.println("  0. Exit");
        return readInt("Choice: ");
    }

    private int adminMenu() {
        System.out.println("\n-------- ADMIN MENU [" + loggedInUser.getName() + "] --------");
        System.out.println("  1. Register new client");
        System.out.println("  2. View all users");
        System.out.println("  3. View all accounts");
        System.out.println("  4. Remove user");
        System.out.println("  5. Save data now");
        System.out.println("  0. Logout");
        return readInt("Choice: ");
    }

    private int clientMenu() {
        System.out.println("\n-------- CLIENT MENU [" + loggedInUser.getName() + "] --------");
        System.out.println("  1. Open new account");
        System.out.println("  2. Deposit");
        System.out.println("  3. Withdraw");
        System.out.println("  4. Transfer");
        System.out.println("  5. Check balance");
        System.out.println("  6. Transaction history");
        System.out.println("  7. My accounts");
        System.out.println("  0. Logout");
        return readInt("Choice: ");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  AUTH
    // ══════════════════════════════════════════════════════════════════════════
    private void login() {
        System.out.println("\n-- Login --");
        String email    = readString("Email: ");
        String password = readString("Password: ");
        for (User u : users) {
            if (u.login(email, password)) {
                loggedInUser = u;
                System.out.println("Welcome, " + u.getName()
                        + "! (" + u.getClass().getSimpleName() + ")");
                return;
            }
        }
        System.out.println("Invalid email or password.");
    }

    private void logout() {
        System.out.println(loggedInUser.getName() + " logged out.");
        loggedInUser = null;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  REGISTRATION
    // ══════════════════════════════════════════════════════════════════════════
    private void registerClient() {
        System.out.println("\n-- Register New Client --");
        System.out.println("  1. Standard  (withdrawal limit, no loan/insurance)");
        System.out.println("  2. Premium   (loan up to 50,000 + insurance 10,000)");
        System.out.println("  3. First Class (loan up to 100,000 + insurance 25,000)");
        int type = readInt("Client type: ");
        if (type < 1 || type > 3) { System.out.println("Invalid type."); return; }

        String id    = generateUserId();
        String name  = readString("Full name: ");
        String email = readString("Email: ");

        for (User u : users) {
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already registered.");
                return;
            }
        }

        String password = readString("Password: ");
        String phone    = readString("Phone number: ");

        Client newClient;
        switch (type) {
            case 1:
                double wLimit = readDouble("Withdrawal limit per transaction: ");
                newClient = new StandardClient(id, name, email, password, phone, wLimit);
                break;
            case 2:
                newClient = new PremiumClient(id, name, email, password, phone, 50000, 10000);
                break;
            default:
                newClient = new FirstClassClient(id, name, email, password, phone, 1, 100000, 25000);
                break;
        }

        users.add(newClient);
        saveToFile();
        System.out.println("Registered! Your ID: " + id);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  ADMIN OPERATIONS
    // ══════════════════════════════════════════════════════════════════════════
    private void viewAllUsers() {
        System.out.println("\n-- All Users --");
        if (users.isEmpty()) { System.out.println("No users."); return; }
        for (User u : users) System.out.println(u);
    }

    private void viewAllAccounts() {
        System.out.println("\n-- All Accounts --");
        if (accounts.isEmpty()) { System.out.println("No accounts."); return; }
        for (Account a : accounts) System.out.println(a);
    }

    private void removeUserFlow() {
        String id = readString("User ID to remove: ");
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(id)) {
                System.out.println("Removed: " + users.get(i).getName());
                users.remove(i);
                saveToFile();
                return;
            }
        }
        System.out.println("User not found.");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  CLIENT OPERATIONS
    // ══════════════════════════════════════════════════════════════════════════
    private void openAccountFlow() {
        System.out.println("\n-- Open New Account --");
        System.out.println("  1. Savings Account");
        System.out.println("  2. Current Account (with overdraft)");
        System.out.println("  3. Business Account");
        int type = readInt("Account type: ");

        double initialDeposit = readDouble("Initial deposit: ");
        if (initialDeposit < 0) { System.out.println("Must be positive."); return; }

        String accNum = generateAccountNumber();
        Client client = (Client) loggedInUser;
        TransactionHistory history = new TransactionHistory();
        Account account;

        switch (type) {
            case 1:
                double rate = readDouble("Interest rate (e.g. 0.05 for 5%): ");
                account = new SavingsAccount(accNum, initialDeposit, client,
                        AccountStatus.ACTIVE, history, rate);
                break;
            case 2:
                double overdraft = readDouble("Overdraft limit: ");
                account = new CurrentAccount(accNum, initialDeposit, client,
                        AccountStatus.ACTIVE, history, overdraft);
                break;
            case 3:
                String bizName = readString("Business name: ");
                account = new BusinessAccount(accNum, initialDeposit, client,
                        AccountStatus.ACTIVE, history, bizName);
                break;
            default:
                System.out.println("Invalid type.");
                return;
        }

        accounts.add(account);
        client.addAccount(account);
        saveToFile();
        System.out.println("Account opened! Number: " + accNum
                + "  Balance: " + initialDeposit);
    }

    private void depositFlow() {
        Account acc = pickMyAccount();
        if (acc == null) return;
        double amount = readDouble("Deposit amount: ");
        try {
            transactionManager.deposit(acc, amount);
            System.out.println("Success. New balance: " + acc.getBalance());
            saveToFile();
        } catch (Exception e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
    }

    private void withdrawFlow() {
        Account acc = pickMyAccount();
        if (acc == null) return;
        double amount = readDouble("Withdrawal amount: ");
        try {
            transactionManager.withdraw(acc, amount);
            System.out.println("Success. New balance: " + acc.getBalance());
            saveToFile();
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    private void transferFlow() {
        System.out.println("\n-- Transfer --");
        Account from = pickMyAccount();
        if (from == null) return;

        String destNum = readString("Destination account number: ");
        Account to = findAccountByNumber(destNum);
        if (to == null)   { System.out.println("Destination not found."); return; }
        if (to == from)   { System.out.println("Cannot transfer to same account."); return; }

        double amount = readDouble("Transfer amount: ");
        try {
            transactionManager.transfer(from, to, amount);
            System.out.println("Success. Your balance: " + from.getBalance());
            saveToFile();
        } catch (InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    private void viewBalanceFlow() {
        Account acc = pickMyAccount();
        if (acc == null) return;
        System.out.println("Balance [" + acc.getAccountNumber() + "]: " + acc.getBalance());
    }

    private void viewHistoryFlow() {
        Account acc = pickMyAccount();
        if (acc == null) return;
        System.out.println("\n-- Transaction History [" + acc.getAccountNumber() + "] --");
        transactionManager.printTransactionHistory(acc);
    }

    private void myAccountsFlow() {
        ((Client) loggedInUser).displayAccounts();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════════════════════════════════════
    private Account pickMyAccount() {
        Client client = (Client) loggedInUser;
        ArrayList<Account> mine = client.getAccounts();
        if (mine.isEmpty()) { System.out.println("You have no accounts yet."); return null; }

        System.out.println("\nYour accounts:");
        for (int i = 0; i < mine.size(); i++) {
            Account a = mine.get(i);
            System.out.println("  " + (i + 1) + ". " + a.getAccountNumber()
                    + "  [" + a.getClass().getSimpleName() + "]"
                    + "  Balance: " + a.getBalance());
        }
        int idx = readInt("Select account: ") - 1;
        if (idx < 0 || idx >= mine.size()) { System.out.println("Invalid."); return null; }
        return mine.get(idx);
    }

    public User findUserById(String userId) {
        for (User u : users) if (u.getUserId().equals(userId)) return u;
        return null;
    }

    public Account findAccountByNumber(String accNum) {
        for (Account a : accounts) if (a.getAccountNumber().equals(accNum)) return a;
        return null;
    }

    private String generateUserId() {
        return "USR" + String.format("%04d", users.size() + 1);
    }

    private String generateAccountNumber() {
        return "ACC" + String.format("%06d", accounts.size() + 1);
    }

    // ── Input readers ─────────────────────────────────────────────────────────
    private int readInt(String prompt) {
        System.out.print(prompt);
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        try { return Double.parseDouble(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
*/