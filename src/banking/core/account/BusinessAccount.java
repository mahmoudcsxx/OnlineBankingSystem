/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Youssef Hassan (250498) — Group A-12
 * @version 1.0
 * @since 19-3-2026
 */

package banking.core.account;

import banking.common.AccountStatus;
import banking.core.user.Client;
import banking.core.transaction.TransactionHistory;

/**
 * Business account that inherits its features from its abstract class
 **/
public class BusinessAccount extends Account {

    private final String businessName;

    public BusinessAccount(String accountNumber, double balance, Client owner, AccountStatus status, TransactionHistory transactionHistory, String businessName) {
        super(accountNumber, balance, owner, status, transactionHistory);
        this.businessName = businessName;
    }

    /**
     * @return the name of the business
      */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @return the overridden version of the {toString} method
     **/
    @Override
    public String toString() {
        return "BusinessAccount --> businessName: " + businessName + " / " + super.toString();
    }

}
