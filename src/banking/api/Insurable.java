//Zeina

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
