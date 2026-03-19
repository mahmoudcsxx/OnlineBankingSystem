/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Mahmoud Samir (257678) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */

package banking.core.user;

import banking.common.ClientType;
import banking.core.account.Account;
import java.util.ArrayList;

/**
 * Abstract base class representing a client in the banking system.
 * Extended by specific client types (e.g. FirstClassClient, PremiumClient and StandardClient).
 */
public abstract class Client extends User {

    protected String phoneNumber;
    protected ArrayList<Account> accounts;
    protected ClientType clientType;

    /** Creates a Client with default values and an empty accounts list. */
    public Client() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Creates a Client with the given details and an empty accounts list.
     *
     * @param userId      Unique identifier for the client.
     * @param name        Full name of the client.
     * @param email       Email address of the client.
     * @param password    Password for the client account.
     * @param phoneNumber Contact phone number of the client.
     * @param clientType  The category/type of this client.
     */
    public Client(String userId, String name, String email, String password,
                  String phoneNumber, ClientType clientType) {
        super(userId, name, email, password);
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds an account to this client's account list.
     * Ignores null accounts and duplicate accounts.
     *
     * @param account The account to add.
     */
    public void addAccount(Account account) {
        if (account != null && !accounts.contains(account)) {
            accounts.add(account);
        }
    }

    /**
     * Removes an account from this client's list by account number.
     *
     * @param accountNumber The account number to search for and remove.
     * @return true if the account was found and removed, false otherwise.
     */
    public boolean removeAccount(String accountNumber) {
        if (accountNumber == null) {
            return false;
        }
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accountNumber)) {
                accounts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for an account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return The matching Account, or null if not found.
     */
    public Account findAccountByNumber(String accountNumber) {
        if (accountNumber == null) {
            return null;
        }
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Returns the balance of the account with the given number.
     *
     * @param accountNumber The account number to check.
     * @return The balance of the account.
     * @throws IllegalArgumentException if the account is not found.
     */
    public double viewBalance(String accountNumber) {
        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
        return account.getBalance();
    }

    /** Prints all accounts belonging to this client to the console. */
    public void displayAccounts() {
        if (accounts.isEmpty()) {
            System.out.println(name + " has no accounts.");
            return;
        }
        System.out.println("Accounts of " + name + ":");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    /**
     * Returns a copy of the client's account list to prevent external modification.
     *
     * @return A new ArrayList containing the client's accounts.
     */
    public ArrayList<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    /** @return The client's phone number. */
    public String getPhoneNumber() { return phoneNumber; }

    /** @param phoneNumber The new phone number to set. */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /** @return The client's type/category. */
    public ClientType getClientType() { return clientType; }

    /** @param clientType The new client type to set. */
    public void setClientType(ClientType clientType) { this.clientType = clientType; }

    @Override
    public String toString() {
        return String.format(
                "Client{userId='%s', name='%s', email='%s', phoneNumber='%s', clientType=%s, accountsCount=%d}",
                userId, name, email, phoneNumber, clientType, accounts.size()
        );
    }
}