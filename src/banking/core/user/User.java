package banking.core.user;
/**
 * Abstract base class representing a user in the banking system.
 * All user types (Client, Admin) extend this class.
 */
public abstract class User{
    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    /** Creates a User with default values. */
    public User(){}
    /**
     * Creates a User with the given details.
     *
     * @param userId   Unique identifier for the user.
     * @param name     Full name of the user.
     * @param email    Email address of the user.
     * @param password Password for the user account.
     */
    public User(String userId,String name, String email, String password){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    /**
     * Validates the provided credentials against the stored ones.
     *
     * @param email    The email to check.
     * @param password The password to check.
     * @return true if both email and password match, false otherwise.
     */
    public boolean login(String email, String password){
        return  this.email != null
                && this.password != null
                && this.email.equals(email)
                && this.password.equals(password);
    }

    /** Logs the user out and prints a confirmation message. */
    public void logout(){
        System.out.println(name + " logged out successfully.");
    }

    /** @return The user's unique identifier. */
    public String getUserId(){
        return userId;
    }

    /** @param userId The new userId to set. */
    public void setUserId(String userId){
        this.userId = userId;
    }

    /** @return The user's full name. */
    public String getName(){
        return name;
    }

    /** @param name The new name to set. */
    public  void setName(String name){
        this.name = name;
    }

    /** @return The user's email address. */
    public String getEmail(){
        return email;
    }

    /** @param email The new email to set. */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * WARNING: Exposing passwords in plain text is a security risk.
     * Passwords should be hashed and never returned directly.
     *
     * @return The user's password.
     */
    public String getPassword(){
        return password;
    }
    /**
     * WARNING: Passwords should be hashed before storing, not saved as plain text.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password){
        this.password = password;
    }
    @Override
    public String toString() {
        return String.format(
                "User{userId='%s', name='%s', email='%s'}",
                userId, name, email
        );
    }
}