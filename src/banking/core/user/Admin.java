package banking.core.user;

import banking.core.account.Account;

import java.security.PublicKey;
import java.util.ArrayList;

public class Admin extends User {

    public Admin(){
        super();
    }
    public Admin(String userId, String name, String email, String password){
        super(userId, name, email, password);
    }
    public void createUser(ArrayList<User> users , User user){
        if(users !=null &&  user !=null){
            users.add(user);
            System.out.println("User created successfully" + user.getName());
        }
    }
    public boolean removeUser(ArrayList<User> users , String userId){
        if(users != null && userId == null){
            return false;
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }
    public void viewAllUsers(ArrayList<User> users){
        if(users == null || users.isEmpty()){
            System.out.println("No users found.");
            return;
        }
        System.out.println("All Users:");
        for(User user : users){
            System.out.println(user);
        }
    }
    public void viewAllAccounts(ArrayList<Account> accounts){
        if(accounts == null || accounts.isEmpty()){
            System.out.println("No accounts found");
            return;
        }
        System.out.println("All Accounts:");
        for(Account account : accounts){
            System.out.println(account);
        }
    }
    @Override
    public String toString(){
        return  String.format(
        "Admin{userId='%s', name='%s', email='%s'}",
                userId, name, email
        );
    }
}
