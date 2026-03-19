/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Malak Waleed (252304) — Group A-7
 * @version 1.0
 * @since 19-3-2026
 */

package banking.presentation;

import java.util.Scanner;

import banking.core.account.Account;
import banking.service.TransactionManager;
import banking.exception.InsufficientFundsException;

public class TransactionView {

    final Scanner scanner;
    private final TransactionManager transactionManager;

    public TransactionView(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.scanner = new Scanner(System.in);
    }


    public void showMenu(Account account) {
        int choice;

        do {
            System.out.println("\n=== Transaction Menu ===");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleDeposit(account);
                    break;
                case 2:
                    handleWithdraw(account);
                    break;
                case 3:
                    handleTransfer(account);
                    break;
                case 4:
                    System.out.println("Exiting Transaction Menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);
    }


    private void handleDeposit(Account account) {
        try {
            System.out.print("Enter deposit amount: ");
            double amount = scanner.nextDouble();

            transactionManager.deposit(account, amount);

            System.out.println("Deposit successful!");
            System.out.println("New Balance: " + account.getBalance());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    private void handleWithdraw(Account account) {
        try {
            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();

            transactionManager.withdraw(account, amount);

            System.out.println("Withdrawal successful!");
            System.out.println("New Balance: " + account.getBalance());

        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void handleTransfer(Account fromAccount) {
        try {
            System.out.print("Enter destination account number: ");
            String accountNumber = scanner.next();

            System.out.print("Enter transfer amount: ");
            double amount = scanner.nextDouble();

            // You should get this from BankSystem later
            Account toAccount = findAccountByNumber(accountNumber);

            if (toAccount == null) {
                System.out.println("Destination account not found.");
                return;
            }

            transactionManager.transfer(fromAccount, toAccount, amount);

            System.out.println("Transfer successful!");
            System.out.println("Your New Balance: " + fromAccount.getBalance());

        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private Account findAccountByNumber(String accountNumber) {
        return null;
    }
}
