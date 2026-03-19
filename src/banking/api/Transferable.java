/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Zeina Alaaeldin (254588) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */

package banking.api;

import banking.core.account.Account;
/**
*Interface that defines transfer capability between accounts.
*/
public interface Transferable {
    /**
     * Transfers money to another account.
     * @param destination the account receiving the money
     * @param amount amount to transfer
     */
    void transfer(Account destination, double amount);
    /**
     * Returns current balance.
     */
    double getBalance();
}

