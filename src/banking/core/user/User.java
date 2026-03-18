package banking.core.user;

public abstract class User{
    protected String userId;
    protected String name;
    protected String email;
    protected String password;

    public User(){

    }
    public User(String userId,String name, String email, String password){
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.password = password;
    }
    public boolean login(String email, String password){
    return  this.email != null
            && this.password != null
            && this.email.equals(email)
            && this.password.equals(password);
    }
    public void logout(){
    System.out.println(name + " logged out successfully.");
    }
    /**
     * Gets the user's unique identifier.
     * @return the userId
     */
    public String getUserId(){
    return userId;
    }
    /**
     * Sets the user's unique identifier.
     * @param userId the userId to set
     */
    public void setUserId(String userId){
    this.userId = userId;
    }
    /**
     * Gets the user's full name.
     * @return the name
     */
    public String getName(){
    return name;
    }
    /**
     * Sets the user's full name.
     * @param name the name to set
     */
    public  void setName(String name){
    this.name = name;
    }
    /**
     * Gets the user's email address.
     * @return the email
     */
    public String getEmail(){
    return email;
}
    /**
     * Sets the user's email address.
     * @param email the email to set
     */
    public void setEmail(String email){
    this.email = email;
    }
    /*Warning*/
    public String getPassword(){
    return password;
    }
    /*Warning*/
    public void setPassword(String password){
    this.password = password;
    }
    public String toString() {
        return String.format(
                "User{userId='%s', name='%s', email='%s'}",
                userId, name, email
        );
    }
    }


