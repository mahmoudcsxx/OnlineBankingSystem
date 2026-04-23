/**
 * Online Banking System
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * Phase 2:
 *  @author Youssef Hassan (250498) - Group A-14
 *  @version 2.0
 *  @since 11-4-2026
 */
package banking.gui;

import banking.common.AccountStatus;
import banking.common.AccountType;
import banking.common.ClientType;
import banking.core.account.Account;
import banking.core.account.BusinessAccount;
import banking.core.account.CurrentAccount;
import banking.core.account.SavingsAccount;
import banking.core.user.Client;
import banking.core.user.FirstClassClient;
import banking.core.user.PremiumClient;
import banking.core.user.StandardClient;
import banking.core.user.User;
import banking.exception.AccountNotFoundException;
import banking.exception.BankException;
import banking.persistence.FileManager;
import banking.core.transaction.TransactionHistory;
import banking.service.AuthService;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AdminDashboardFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminDashboardFrame.class.getName());
    private final FileManager fileManager = new FileManager("data/bank");

    /**
     * Creates new form AdminDashboardFrame
     */
    public AdminDashboardFrame() {
        initComponents();
        wireListeners();
    }

    /** Attaches handlers to all dashboard buttons */
    private void wireListeners() {
        jButton1.addActionListener(e -> handleLogout());
        jButton2.addActionListener(e -> handleLogout());
        jButton3.addActionListener(e -> handleViewUsers());
        jButton4.addActionListener(e -> handleViewAccounts());
        jButton5.addActionListener(e -> handleRemoveUser());
        jButton6.addActionListener(e -> handleCreateClient());
        jButton7.addActionListener(e -> handleCreateAccount());
    }

    /** Logs out and returns to the login screen */
    private void handleLogout() {
        AuthService.get().logout();
        new LoginFrame().setVisible(true);
        dispose();
    }

    /** Shows every registered user in a dialog */
    private void handleViewUsers() {
        try {
            ArrayList<User> users = AuthService.get().getUsers();
            if (users.isEmpty()) {
                throw new BankException("No users registered yet.");
            }
            StringBuilder sb = new StringBuilder("All users:\n\n");
            for (User u : users) {
                sb.append("• ").append(u.getName())
                  .append(" (").append(u.getEmail()).append(")\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(),
                    "Registered Users", JOptionPane.INFORMATION_MESSAGE);
        } catch (BankException ex) {
            showError(ex.getMessage());
        }
    }

    /** Shows every account from every client. Throws AccountNotFoundException when empty. */
    private void handleViewAccounts() {
        try {
            ArrayList<Account> all = collectAllAccounts();
            if (all.isEmpty()) {
                throw new AccountNotFoundException("No accounts exist in the system.");
            }
            StringBuilder sb = new StringBuilder("All accounts:\n\n");
            for (Account a : all) {
                sb.append(a.getAccountNumber())
                  .append("  —  $").append(a.getBalance())
                  .append("  (").append(a.getStatus()).append(")");
                if (a.getOwner() != null) {
                    sb.append("  owner: ").append(a.getOwner().getName());
                }
                sb.append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(),
                    "All Accounts", JOptionPane.INFORMATION_MESSAGE);
        } catch (BankException ex) {
            showError(ex.getMessage());
        }
    }

    /** Prompts for an email, then removes that user (cannot remove self) */
    private void handleRemoveUser() {
        try {
            String email = prompt("Enter the email of the user to remove:");
            if (email == null || email.isBlank()) return;

            ArrayList<User> users = AuthService.get().getUsers();
            User target = null;
            for (User u : users) {
                if (email.equalsIgnoreCase(u.getEmail())) {
                    target = u;
                    break;
                }
            }
            if (target == null) {
                throw new BankException("No user found with email: " + email);
            }
            if (target == AuthService.get().getCurrentUser()) {
                throw new BankException("You cannot remove yourself while logged in.");
            }

            users.remove(target);
            fileManager.saveUsers(users);
            fileManager.saveAccounts(collectAllAccounts());
            JOptionPane.showMessageDialog(this,
                    "Removed user: " + target.getName(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (BankException ex) {
            showError(ex.getMessage());
        }
    }

    /** Creates a new client from a small prompt dialog flow */
    private void handleCreateClient() {
        try {
            String name = prompt("New client — full name:");
            if (name == null || name.isBlank()) return;
            String email = prompt("Email:");
            if (email == null || email.isBlank()) return;
            String password = prompt("Password:");
            if (password == null || password.isBlank()) return;
            String phone = prompt("Phone number:");
            if (phone == null || phone.isBlank()) return;
            ClientType clientType = chooseClientType();
            if (clientType == null) return;

            String userId = java.util.UUID.randomUUID().toString().substring(0, 8);
            Client client = buildClientFor(clientType, userId, name, email, password, phone);

            AuthService.get().register(client);
            JOptionPane.showMessageDialog(this,
                    clientType + " client created: " + name,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (BankException ex) {
            showError(ex.getMessage());
        }
    }

    /** Creates an account for an existing client and persists all accounts */
    private void handleCreateAccount() {
        try {
            String email = prompt("Enter the client email:");
            if (email == null || email.isBlank()) return;

            Client client = findClientByEmail(email);
            if (client == null) {
                throw new BankException("No client found with email: " + email);
            }

            AccountType accountType = chooseAccountType();
            if (accountType == null) return;

            String openingBalanceText = prompt("Opening balance:");
            if (openingBalanceText == null || openingBalanceText.isBlank()) return;

            double openingBalance;
            try {
                openingBalance = Double.parseDouble(openingBalanceText.trim());
            } catch (NumberFormatException ex) {
                throw new BankException("Opening balance must be a valid number.");
            }
            if (openingBalance < 0) {
                throw new BankException("Opening balance cannot be negative.");
            }

            Account account = buildAccountFor(client, accountType, openingBalance);
            client.addAccount(account);
            fileManager.saveAccounts(collectAllAccounts());

            JOptionPane.showMessageDialog(this,
                    "Created " + accountType + " account " + account.getAccountNumber()
                            + " for " + client.getName(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (BankException ex) {
            showError(ex.getMessage());
        }
    }

    /** Gathers every account owned by any client in the system */
    private ArrayList<Account> collectAllAccounts() {
        ArrayList<Account> all = new ArrayList<>();
        for (User u : AuthService.get().getUsers()) {
            if (u instanceof Client) {
                all.addAll(((Client) u).getAccounts());
            }
        }
        return all;
    }

    private Client findClientByEmail(String email) {
        for (User user : AuthService.get().getUsers()) {
            if (user instanceof Client && email.equalsIgnoreCase(user.getEmail())) {
                return (Client) user;
            }
        }
        return null;
    }

    private AccountType chooseAccountType() {
        Object selected = JOptionPane.showInputDialog(
                this,
                "Choose account type:",
                "Create Account",
                JOptionPane.PLAIN_MESSAGE,
                null,
                AccountType.values(),
                AccountType.SAVINGS
        );
        return selected instanceof AccountType ? (AccountType) selected : null;
    }

    private ClientType chooseClientType() {
        Object selected = JOptionPane.showInputDialog(
                this,
                "Choose client type:",
                "Create Client",
                JOptionPane.PLAIN_MESSAGE,
                null,
                ClientType.values(),
                ClientType.STANDARD
        );
        return selected instanceof ClientType ? (ClientType) selected : null;
    }

    private Client buildClientFor(ClientType clientType, String userId, String name,
                                  String email, String password, String phone) {
        switch (clientType) {
            case PREMIUM:
                return new PremiumClient(userId, name, email, password, phone, 50000.0, 10000.0);
            case FIRST_CLASS:
                return new FirstClassClient(userId, name, email, password, phone, 1, 100000.0, 25000.0);
            case STANDARD:
            default:
                return new StandardClient(userId, name, email, password, phone, 1000.0);
        }
    }

    private Account buildAccountFor(Client client, AccountType accountType, double openingBalance) {
        String accountNumber = "ACC-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        TransactionHistory history = new TransactionHistory();

        switch (accountType) {
            case CURRENT:
                return new CurrentAccount(accountNumber, openingBalance, client,
                        AccountStatus.ACTIVE, history, 1000.0);
            case BUSINESS:
                String businessName = prompt("Business name:");
                if (businessName == null || businessName.isBlank()) {
                    businessName = client.getName() + " Business";
                }
                return new BusinessAccount(accountNumber, openingBalance, client,
                        AccountStatus.ACTIVE, history, businessName);
            case SAVINGS:
            default:
                return new SavingsAccount(accountNumber, openingBalance, client,
                        AccountStatus.ACTIVE, history, 0.05);
        }
    }

    /** Small wrapper for JOptionPane input prompts */
    private String prompt(String message) {
        return JOptionPane.showInputDialog(this, message);
    }

    /** Small wrapper for error message dialogs */
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        Right = new javax.swing.JPanel();
        Left = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("Logout");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nova Bank System - Admin Dashboard");
        setPreferredSize(new java.awt.Dimension(800, 580));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 580));
        getContentPane().setLayout(null);

        Right.setBackground(new java.awt.Color(255, 255, 255));

        Left.setBackground(new java.awt.Color(11, 60, 93));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("NovaBank");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Banking, reimagined.");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 51));
        jLabel10.setText("© 2026 NovaBank. All rights reserved.");

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Logout");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Admin Dashboard");

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(LeftLayout.createSequentialGroup()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(87, 87, 87)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(86, 86, 86)
                .addComponent(jLabel10)
                .addGap(27, 27, 27)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(11, 60, 93));
        jLabel1.setText("Dashboard");

        jButton3.setBackground(new java.awt.Color(11, 60, 93));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("View all users");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Select the operation you would like to use ");

        jButton4.setBackground(new java.awt.Color(11, 60, 93));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("View all accounts");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setBackground(new java.awt.Color(11, 60, 93));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Remove user");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButton6.setBackground(new java.awt.Color(11, 60, 93));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Create client");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jButton7.setBackground(new java.awt.Color(11, 60, 93));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Create client account");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Logged in as: ADMIN");

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(RightLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(26, 26, 26))
        );

        getContentPane().add(Right);
        Right.setBounds(0, 0, 800, 545);

        getAccessibleContext().setAccessibleName("Client Dashboard");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AdminDashboardFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Right;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
