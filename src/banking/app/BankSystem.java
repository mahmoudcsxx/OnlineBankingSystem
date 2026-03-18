package banking.app;

import banking.common.AccountStatus;
import banking.common.ClientType;
import banking.core.account.*;
import banking.core.transaction.TransactionHistory;
import banking.core.user.*;
import banking.exception.InsufficientFundsException;
import banking.persistence.FileManager;
import banking.service.TransactionManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * BankSystem — main entry point for the Online Banking System.
 *
 * Responsibilities:
 *  - Holds the master lists of users and accounts
 *  - Provides a login/register flow
 *  - Dispatches to an Admin menu or a Client menu based on who logged in
 *  - Delegates all transaction work to TransactionManager
 *  - Persists data via FileManager on exit
 */
public class BankSystem {

    // ─── State ────────────────────────────────────────────────────────────────
    private final ArrayList<User>    users    = new ArrayList<>();
    private final ArrayList<Account> accounts = new ArrayList<>();

    private final TransactionManager transactionManager = new TransactionManager();
    private final FileManager        fileManager        = new FileManager("data/bank");
    private final Scanner            scanner            = new Scanner(System.in);

    // ─── Entry point ──────────────────────────────────────────────────────────
    public static void main(String[] args) {
        new BankSystem().run();
    }

    // ─── Bootstrap ────────────────────────────────────────────────────────────

    /** Seeds demo data and starts the main loop. */
    public void run() {
        seedDemoData();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   Welcome to Core Bank System║");
        System.out.println("╚══════════════════════════════╝");

        boolean running = true;
        while (running) {
            running = showMainMenu();
        }

        saveAll();
        System.out.println("Goodbye!");
    }

    /** Pre-loads one admin, one of each client tier, and matching accounts. */
    private void seedDemoData() {
        // Admin
        Admin admin = new Admin("A001", "Admin", "admin@bue.edu.eg", "admin123");
        users.add(admin);

        // Standard client
        StandardClient std = new StandardClient(
                "C001", "Mahmoud Ali", "mahmoud@bue.edu.eg", "pass123",
                "01012345678", 2000.0);
        SavingsAccount stdAcc = new SavingsAccount(
                "ACC-001", 5000.0, std, AccountStatus.ACTIVE,
                new TransactionHistory(), 0.05);
        std.addAccount(stdAcc);
        users.add(std);
        accounts.add(stdAcc);

        // Premium client
        PremiumClient prem = new PremiumClient(
                "C002", "Sara Hassan", "sara@bue.edu.eg", "pass456",
                "01098765432", 50000.0, 10000.0);
        PremiumAccount premAcc = new PremiumAccount(
                "ACC-002", 20000.0, prem, AccountStatus.ACTIVE,
                new TransactionHistory(), 5000.0);
        CurrentAccount premCurr = new CurrentAccount(
                "ACC-003", 8000.0, prem, AccountStatus.ACTIVE,
                new TransactionHistory(), 1000.0);
        prem.addAccount(premAcc);
        prem.addAccount(premCurr);
        users.add(prem);
        accounts.add(premAcc);
        accounts.add(premCurr);

        // First-class client
        FirstClassClient fc = new FirstClassClient(
                "C003", "Zeina Mostafa", "zeina@bue.edu.eg", "pass789",
                "01155556666", 1, 100000.0, 25000.0);
        BusinessAccount fcBiz = new BusinessAccount(
                "ACC-004", 150000.0, fc, AccountStatus.ACTIVE,
                new TransactionHistory(), "Zeina Enterprises");
        fc.addAccount(fcBiz);
        users.add(fc);
        accounts.add(fcBiz);
    }

    // ─── Main Menu ────────────────────────────────────────────────────────────

    /**
     * Displays the top-level menu.
     * @return false when the user chooses to exit
     */
    private boolean showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choice: ");

        int choice = readInt();
        switch (choice) {
            case 1: login();    break;
            case 2: register(); break;
            case 3: return false;
            default: System.out.println("Invalid choice.");
        }
        return true;
    }

    // ─── Auth ─────────────────────────────────────────────────────────────────

    private void login() {
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        for (User u : users) {
            if (u.login(email, password)) {
                System.out.println("\nWelcome back, " + u.getName() + "!");
                if (u instanceof Admin)  { adminMenu((Admin) u); }
                else if (u instanceof Client) { clientMenu((Client) u); }
                return;
            }
        }
        System.out.println("Invalid email or password.");
    }

    private void register() {
        System.out.println("\n=== Register New Client ===");
        System.out.print("Full name: ");      scanner.nextLine(); // flush
        String name  = scanner.nextLine();
        System.out.print("Email: ");          String email    = scanner.next();
        System.out.print("Password: ");       String password = scanner.next();
        System.out.print("Phone number: ");   String phone    = scanner.next();

        // Check for duplicate email
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                System.out.println("An account with that email already exists.");
                return;
            }
        }

        String newId = "C" + String.format("%03d", users.size() + 1);
        StandardClient client = new StandardClient(
                newId, name, email, password, phone, 1000.0);

        // Open a default savings account
        String accNum = "ACC-" + String.format("%03d", accounts.size() + 1);
        SavingsAccount acc = new SavingsAccount(
                accNum, 0.0, client, AccountStatus.ACTIVE,
                new TransactionHistory(), 0.03);
        client.addAccount(acc);
        users.add(client);
        accounts.add(acc);

        System.out.println("Registration successful!");
        System.out.println("Your account number: " + accNum);
    }

    // ─── Admin Menu ───────────────────────────────────────────────────────────

    private void adminMenu(Admin admin) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n=== Admin Panel ===");
            System.out.println("1. View all users");
            System.out.println("2. View all accounts");
            System.out.println("3. Remove user");
            System.out.println("4. Create standard client");
            System.out.println("5. Logout");
            System.out.print("Choice: ");

            switch (readInt()) {
                case 1: admin.viewAllUsers(users); break;
                case 2: admin.viewAllAccounts(accounts); break;
                case 3: adminRemoveUser(admin); break;
                case 4: adminCreateClient(admin); break;
                case 5: admin.logout(); loggedIn = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void adminRemoveUser(Admin admin) {
        System.out.print("Enter user ID to remove: ");
        String id = scanner.next();
        boolean removed = admin.removeUser(users, id);
        System.out.println(removed ? "User removed." : "User not found.");
    }

    private void adminCreateClient(Admin admin) {
        System.out.println("\n-- Create Standard Client --");
        System.out.print("User ID: ");   String uid   = scanner.next();
        System.out.print("Name: ");      scanner.nextLine(); String name = scanner.nextLine();
        System.out.print("Email: ");     String email = scanner.next();
        System.out.print("Password: "); String pass  = scanner.next();
        System.out.print("Phone: ");     String phone = scanner.next();
        System.out.print("Withdraw limit: "); double limit = readDouble();

        StandardClient client = new StandardClient(uid, name, email, pass, phone, limit);
        String accNum = "ACC-" + String.format("%03d", accounts.size() + 1);
        SavingsAccount acc = new SavingsAccount(
                accNum, 0.0, client, AccountStatus.ACTIVE,
                new TransactionHistory(), 0.03);
        client.addAccount(acc);
        admin.createUser(users, client);
        accounts.add(acc);
        System.out.println("Account created: " + accNum);
    }

    // ─── Client Menu ──────────────────────────────────────────────────────────

    private void clientMenu(Client client) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n=== Client Dashboard — " + client.getName() + " ===");
            System.out.println("Client type : " + client.getClientType());
            System.out.println("1. View my accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction history");
            if (client instanceof PremiumClient || client instanceof FirstClassClient) {
                System.out.println("6. Apply for loan");
                System.out.println("7. Claim insurance");
            }
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = readInt();
            switch (choice) {
                case 1: client.displayAccounts(); break;
                case 2: handleDeposit(client); break;
                case 3: handleWithdraw(client); break;
                case 4: handleTransfer(client); break;
                case 5: handleHistory(client); break;
                case 6: handleLoan(client); break;
                case 7: handleInsurance(client); break;
                case 0: client.logout(); loggedIn = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ─── Client Operations ────────────────────────────────────────────────────

    private void handleDeposit(Client client) {
        Account acc = selectAccount(client);
        if (acc == null) return;
        System.out.print("Amount to deposit: ");
        double amount = readDouble();
        transactionManager.deposit(acc, amount);
        System.out.printf("Deposited %.2f — New balance: %.2f%n", amount, acc.getBalance());
    }

    private void handleWithdraw(Client client) {
        Account acc = selectAccount(client);
        if (acc == null) return;

        // Enforce StandardClient withdrawal limit
        if (client instanceof StandardClient) {
            System.out.print("Amount to withdraw: ");
            double amount = readDouble();
            StandardClient sc = (StandardClient) client;
            if (!sc.canWithdraw(amount)) {
                System.out.printf("Exceeds your withdrawal limit of %.2f%n", sc.getWithdrawLimit());
                return;
            }
            try {
                transactionManager.withdraw(acc, amount);
                System.out.printf("Withdrawn %.2f — New balance: %.2f%n", amount, acc.getBalance());
            } catch (InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.print("Amount to withdraw: ");
            double amount = readDouble();
            try {
                transactionManager.withdraw(acc, amount);
                System.out.printf("Withdrawn %.2f — New balance: %.2f%n", amount, acc.getBalance());
            } catch (InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleTransfer(Client client) {
        Account from = selectAccount(client);
        if (from == null) return;

        System.out.print("Destination account number: ");
        String destNum = scanner.next();
        Account to = findAccountByNumber(destNum);
        if (to == null) {
            System.out.println("Destination account not found.");
            return;
        }
        if (to == from) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }

        System.out.print("Amount to transfer: ");
        double amount = readDouble();
        try {
            transactionManager.transfer(from, to, amount);
            System.out.printf("Transferred %.2f — Your new balance: %.2f%n",
                    amount, from.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleHistory(Client client) {
        Account acc = selectAccount(client);
        if (acc == null) return;
        System.out.println("--- Transaction History for " + acc.getAccountNumber() + " ---");
        transactionManager.printTransactionHistory(acc);
    }

    private void handleLoan(Client client) {
        if (client instanceof banking.api.LoanEligible) {
            banking.api.LoanEligible eligible = (banking.api.LoanEligible) client;
            System.out.printf("Your loan limit: %.2f%n", eligible.getLoanLimit());
            System.out.print("Loan amount requested: ");
            double amount = readDouble();
            eligible.applyForLoan(amount);
        } else {
            System.out.println("Your client tier does not support loans.");
        }
    }

    private void handleInsurance(Client client) {
        if (client instanceof banking.api.Insurable) {
            banking.api.Insurable insurable = (banking.api.Insurable) client;
            System.out.printf("Your insurance coverage: %.2f%n", insurable.getInsurance());
            insurable.claimInsurance();
        } else {
            System.out.println("Your client tier does not support insurance.");
        }
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    /**
     * Prints the client's accounts and asks them to pick one.
     * @return the chosen Account, or null if none / invalid.
     */
    private Account selectAccount(Client client) {
        ArrayList<Account> accs = client.getAccounts();
        if (accs.isEmpty()) {
            System.out.println("You have no accounts.");
            return null;
        }
        System.out.println("Your accounts:");
        for (int i = 0; i < accs.size(); i++) {
            System.out.printf("  %d. %s  (balance: %.2f)%n",
                    i + 1, accs.get(i).getAccountNumber(), accs.get(i).getBalance());
        }
        System.out.print("Select account number: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= accs.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        return accs.get(idx);
    }

    /** Searches the global accounts list by account number. */
    private Account findAccountByNumber(String number) {
        for (Account a : accounts) {
            if (a.getAccountNumber().equals(number)) return a;
        }
        return null;
    }

    /** Saves users, accounts, and all transactions on program exit. */
    private void saveAll() {
        fileManager.saveUsers(users);
        fileManager.saveAccounts(accounts);

        ArrayList<banking.core.transaction.Transaction> allTx = new ArrayList<>();
        for (Account a : accounts) {
            allTx.addAll(a.getTransactionHistory().getHistory());
        }
        fileManager.saveTransactions(allTx);
        System.out.println("Data saved.");
    }

    // ─── Safe input readers ───────────────────────────────────────────────────

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.next().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.next().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid amount: ");
            }
        }
    }
}