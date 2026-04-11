
/**
 * Online Banking System 
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt
 
 * Phase - 2
 * @author Youssef Hassan (250498) - Group A-14
 */

package banking.service;

import banking.core.user.User;
import banking.exception.FileAccessException;
import banking.exception.InvalidLoginException;
import banking.exception.UserAlreadyExistsException;
import banking.persistence.FileManager;

import java.util.ArrayList;

/**
 * Holds the shared user list and handles login / register / persistence
 */

public class AuthService {
    
  private static AuthService instance;
  
  private final ArrayList<User> users = new ArrayList<>();
  private final FileManager fileManager = new FileManager("data/bank");
  private User currentUser;
  
  private AuthService() {
      
      try {
          users.addAll(fileManager.loadUsers());
      }
      catch (FileAccessException ignored) {
          // first run - no file yet, start with an empty list
      }
      
  }
    
  public static AuthService get() {
      if(instance == null) instance = new AuthService();
      return instance;
  }
  
  /** Authenticates by email + password. Throws InvalidLoginException on failure **/
  
  public User login(String email, String password) {
      
      for(User u : users) {
      if(u.login(email, password)) {
      currentUser = u;
      return u;
      }
      
      }
      throw new InvalidLoginException("Invalid email or password.");
  }
  
  /** Adds a new user. Throws UserAlreadyExistsException if email is taken */
  public void register(User newUser) {
  for(User u: users) {
  if(u.getEmail() != null && u.getEmail().equalsIgnoreCase(newUser.getEmail())) {
  throw new UserAlreadyExistsException("Email already registered: " + newUser.getEmail());
    }
  }
  users.add(newUser);
  fileManager.saveUsers(users); // may throw FileAccessException
  }
  
  public User getCurrentUser() {
  return currentUser;
  }
  
  public void logout() {
  currentUser = null;
  }
  
  public ArrayList<User> getUsers() {
  return users;
  }
  
}
