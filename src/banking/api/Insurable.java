/**
 * Online Banking System 
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 
 * Phase - 2
 * @author Zeina Alaaeldin (254588) — Group A-14
 * @version 1.0
 * @since 19-3-2026
 */
package banking.api;

public interface Insurable {
    /**
     * Returns the insurance coverage amount available for the client.
     */
    double getInsurance();
    /**
     * Allows the client to claim their insurance.
     */
    void claimInsurance();
}
