/**
 * Online Banking System 
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2 
 * @author Youssef Hassan (250498) — Group A-12
 * @version 1.0
 * @since 19-3-2026
 */

package banking.common;
/* This enum represents the type of bank account  */
public enum AccountType {
    // personal account for saving money {with interest}
    SAVINGS,
    // personal account for transactions {with no limit}
    CURRENT,
    // an account designed for business use
    BUSINESS
}
