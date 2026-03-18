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
 */
public class FileManager
{

    private String filePath;

    // Constructor
    public FileManager(String filePath)
    {
        this.filePath = filePath;
    }

    // SAVE METHODS

    public void saveUsers(ArrayList<User> users)
    {
        try
        {
            PrintWriter writer = new PrintWriter(filePath + "_users.txt");
            for (int i = 0; i < users.size(); i++)
            {
                User u = users.get(i);
                writer.println(u.getUserId() + "," + u.getName() + "," + u.getPassword() + ",STANDARD");
            }
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void saveAccounts(ArrayList<Account> accounts)
    {
        try
        {
            PrintWriter writer = new PrintWriter(filePath + "_accounts.txt");
            for (int i = 0; i < accounts.size(); i++)
            {
                Account a = accounts.get(i);
                writer.println(a.getAccountNumber() + "," + a.getBalance() + "," + a.getStatus());
            }
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public void saveTransactions(ArrayList<Transaction> transactions)
    {
        try
        {
            PrintWriter writer = new PrintWriter(filePath + "_transactions.txt");
            for (int i = 0; i < transactions.size(); i++)
            {
                Transaction t = transactions.get(i);
                writer.println(t.getTransaction()+ "," + t.getAmount() + "," + t.getType() + ",DEPOSIT");
            }
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    //  LOAD METHODS

    public ArrayList<User> loadUsers()
    {
        ArrayList<User> users = new ArrayList<>();
        try
        {
            Scanner scanner = new Scanner(new File(filePath + "_users.txt"));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 3)
                {

                    User u = new StandardClient(parts[0], parts[1], parts[2],
                            "email", "phone", 0);

                    users.add(u);
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    public ArrayList<Account> loadAccounts()
    {
        ArrayList<Account> accounts = new ArrayList<>();
        try
        {
            Scanner scanner = new Scanner(new File(filePath + "_accounts.txt"));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 3)
                {

                    Account a = new SavingsAccount(parts[0], Double.parseDouble(parts[1]), null,
                            AccountStatus.valueOf(parts[2]), new TransactionHistory(), 0.0);
                    accounts.add(a);
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;
    }

    public ArrayList<Transaction> loadTransactions(ArrayList<Account> accounts)
    {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try
        {
            Scanner scanner = new Scanner(new File(filePath + "_transactions.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                Account acc = null;
                for (int i = 0; i < accounts.size(); i++)
                {
                    if (accounts.get(i).getAccountNumber().equals(parts[3]))
                    {
                        acc = accounts.get(i);
                        break;
                    }
                }

                if (acc != null && parts.length >= 4)
                {


                    Transaction t = new Deposit(acc, Double.parseDouble(parts[1]));

                    transactions.add(t);
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }


    // Writes one line to file
    public void writeLine(String data)
    {
        try
        {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath, true));
            writer.println(data);
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Error writing line: " + e.getMessage());
        }
    }


    // Reads all lines from file
    public ArrayList<String> readLines()
    {
        ArrayList<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine())
            {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error reading lines: " + e.getMessage());
        }

        return lines;
    }


    //  GETTER & SETTER
    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
}
