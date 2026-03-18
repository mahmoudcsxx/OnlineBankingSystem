//Zeina

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

