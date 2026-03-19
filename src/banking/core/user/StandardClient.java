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

/**
 * Represents a standard-tier client with a basic withdrawal limit.
 * Extends {@link Client} with no loan or insurance privileges.
 */
public class StandardClient extends Client{
    /** Maximum amount allowed per withdrawal transaction. */
    private double withdrawLimit;

    /**
     * Default constructor. Sets the withdrawal limit to 1,000
     * and client type to {@link ClientType#STANDARD}.
     */
    public StandardClient(){
        super();
        this.clientType = ClientType.STANDARD;
        this.withdrawLimit = 1000; // Default limit
    }
    /**
     * Full constructor. Initializes all client details with a custom withdrawal limit.
     *
     * @param userId       the unique identifier for this client
     * @param name         the full name of the client
     * @param email        the client's email address
     * @param password     the client's account password
     * @param phoneNumber  the client's contact phone number
     * @param withdrawLimit the maximum withdrawal amount per transaction
     */
    public StandardClient(String userId, String name, String email, String password,
                          String phoneNumber, double withdrawLimit) {
        super(userId, name, email, password, phoneNumber, ClientType.STANDARD);
        this.withdrawLimit = withdrawLimit;
    }
    /**
     * Returns the maximum withdrawal amount allowed per transaction.
     *
     * @return the withdrawal limit as a {@code double}
     */
    public double getWithdrawLimit(){
        return withdrawLimit;
    }
    /**
     * Updates the withdrawal limit for this client.
     *
     * @param withdrawLimit the new withdrawal limit (should be a positive value)
     */
    public void setWithdrawLimit(double withdrawLimit){
        this.withdrawLimit = withdrawLimit;
    }
    /**
     * Checks whether the requested amount is within the withdrawal limit.
     *
     * @param amount the amount the client wishes to withdraw
     * @return {@code true} if the amount does not exceed the limit, {@code false} otherwise
     */
    public boolean canWithdraw(double amount){
        return amount <= withdrawLimit;
    }
    /**
     * Returns a formatted summary of this client's key details.
     *
     * @return a string containing the client's ID, name, email, phone, withdrawal limit, and account count
     */
    @Override
    public String toString(){
        return String.format(
                "StandardClient{userId='%s', name='%s', email='%s', phone='%s', withdrawLimit=%.2f, accounts=%d}",
                userId, name, email, phoneNumber, withdrawLimit, accounts.size()
        );
    }
}
