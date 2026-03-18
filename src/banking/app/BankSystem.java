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
