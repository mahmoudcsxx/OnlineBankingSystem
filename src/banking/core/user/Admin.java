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

import banking.core.account.Account;
import java.util.ArrayList;

/**
 * Represents an Admin user with elevated privileges.
 * Admins can manage users and view all accounts in the system.
 */
public class Admin extends User {

    /** Creates an Admin with default values. */
    public Admin() {
        super();
    }

    /**
     * Creates an Admin with the given details.
     *
     * @param userId   Unique identifier for the admin.
     * @param name     Full name of the admin.
     * @param email    Email address of the admin.
     * @param password Password for the admin account.
     */
    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    /**
     * Adds a new user to the system.
     *
     * @param users The list of existing users.
     * @param user  The new user to add.
     */
    public void createUser(ArrayList<User> users, User user) {
        if (users != null && user != null) {
            users.add(user);
            System.out.println("User created successfully: " + user.getName());
        }
    }

    /**
     * Removes a user from the system by their ID.
     *
     * @param users  The list of existing users.
     * @param userId The ID of the user to remove.
     * @return true if the user was found and removed, false otherwise.
     */
    public boolean removeUser(ArrayList<User> users, String userId) {
        if (users == null || userId == null) {
            return false;
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Prints all registered users to the console.
     *
     * @param users The list of users to display.
     */
    public void viewAllUsers(ArrayList<User> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("All Users:");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * Prints all existing accounts to the console.
     *
     * @param accounts The list of accounts to display.
     */
    public void viewAllAccounts(ArrayList<Account> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("All Accounts:");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    /**
     * Returns a formatted summary of this admin's key details.
     *
     * @return a string containing the admin's ID, name, and email.
     */
    @Override
    public String toString() {
        return String.format(
                "Admin{userId='%s', name='%s', email='%s'}",
                userId, name, email
        );
    }
}