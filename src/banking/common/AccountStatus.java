/**
 * Online Banking System — Phase 1
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * @author Youssef Hassan (250498) — Group A-12
 * @version 1.0
 * @since 19-3-2026
 */

package banking.common;
/* This enum represents the status of the bank account  */
public enum AccountStatus {
    // fully functional account
    ACTIVE,
    // temporarily nonfunctional
    SUSPENDED,
    // permanently shutdown account
    CLOSED
}
