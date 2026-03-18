package banking.core.user;

import banking.common.ClientType;
import banking.core.account.Account;

import java.util.ArrayList;

public abstract class Client extends User {
    protected String phoneNumber;
    protected ArrayList<Account> accounts;
    protected ClientType clientType;

    public Client(){
        this.accounts = new ArrayList<>();
    }
    public Client(String userId, String name, String email, String password,
                  String phoneNumber, ClientType clientType){
        super(userId, name, email, password);
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.accounts = new ArrayList<>();
    }
    public void addAccount(Account account){
        if(account != null){
            accounts.add(account);
        }
    }
    public boolean removeAccount(String accountNumber){
        if (accountNumber == null){
            return false;
        }
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getAccountNumber().equals(accountNumber)){
                accounts.remove(i);
                return  true;
            }
        }
        return false;
    }
    public Account findAccountByNumber(String accountNumber){
        if (accountNumber == null){
            return null;
        }
        for(Account account : accounts){
            if(account.getAccountNumber().equals(accountNumber)){
                return account;
            }
        }
        return null;
    }
    public double viewBalance(String accountNumber){
        Account account = findAccountByNumber(accountNumber);
        if(account != null){
            return account.getBalance();
        }
        return -1;
    }
    public void displayAccounts() {
        if (accounts.isEmpty()) {
            System.out.println(name + " has no accounts.");
            return;
        }

        System.out.println("Accounts of " + name + ":");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    /**
     * Gets the client's phone number.
     * @return the phoneNumber
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    /**
     * Gets the client's type/category.
     * @return the clientType enum value
     */
    public ClientType getClientType() {
        return clientType;
    }
    /**
     * Sets the client's type/category.
     * @param clientType the clientType to set
     */
    public void setClientType(ClientType clientType){
        this.clientType = clientType;
    }
    @Override
    public String toString() {
        return String.format(
                "Client{userId='%s', name='%s', email='%s', phoneNumber='%s', clientType=%s, accountsCount=%d}",
                userId, name, email, phoneNumber, clientType, accounts.size()
        );
    }


}
