package banking.core.user;

public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String password;

}
public User() {
}
public User(String userId,String name,String email, String password){
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.password = password;
}
public boolean login (String email){

}

