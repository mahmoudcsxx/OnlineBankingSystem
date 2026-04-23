package banking.gui;

import banking.core.account.Account;
import banking.core.transaction.Deposit;
import banking.core.transaction.Transaction;
import banking.core.transaction.Withdrawal;
import banking.core.user.Client;
import banking.core.user.User;
import banking.exception.BankException;
import banking.exception.InvalidAmountException;
import banking.persistence.FileManager;
import banking.service.AuthService;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DepositWithdrawPanel extends javax.swing.JPanel {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DepositWithdrawPanel.class.getName());

    private final Client currentUser;
    private final FileManager fileManager = new FileManager("data/bank");

    public DepositWithdrawPanel() {
        this(null);
    }

    public DepositWithdrawPanel(Client user) {
        this.currentUser = user;
        initComponents();
        loadAccountsIntoCombo();
        refreshBalanceLabel();
        wireListeners();
    }

    public static void openWindow(Client user) {
        JFrame frame = new JFrame("Nova Bank System - Deposit & Withdraw");
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(new DepositWithdrawPanel(user));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void wireListeners() {
        depositButton.addActionListener(e -> handleTransaction(true));
        withdrawButton.addActionListener(e -> handleTransaction(false));
        accountCombo.addActionListener(e -> refreshBalanceLabel());
        dashboardButton.addActionListener(e -> {
            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void loadAccountsIntoCombo() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (currentUser != null) {
            for (Account account : currentUser.getAccounts()) {
                model.addElement(account.getAccountNumber());
            }
        }
        if (model.getSize() == 0) {
            model.addElement("No accounts");
        }
        accountCombo.setModel(model);
    }

    private Account getSelectedAccount() {
        if (currentUser == null) return null;
        String selected = (String) accountCombo.getSelectedItem();
        if (selected == null || "No accounts".equals(selected)) return null;
        return currentUser.findAccountByNumber(selected);
    }

    private void refreshBalanceLabel() {
        Account account = getSelectedAccount();
        if (account == null) {
            balanceValueLabel.setText("EGP 0.00");
        } else {
            balanceValueLabel.setText(String.format("EGP %.2f", account.getBalance()));
        }
    }

    private void handleTransaction(boolean isDeposit) {
        try {
            Account account = getSelectedAccount();
            if (account == null) {
                throw new BankException("Please select an account first.");
            }

            String raw = (isDeposit ? depositField : withdrawField).getText().trim();
            double amount;
            try {
                amount = Double.parseDouble(raw);
            } catch (NumberFormatException ex) {
                throw new InvalidAmountException("Amount must be a valid number.");
            }

            if (amount <= 0) {
                throw new InvalidAmountException("Amount must be greater than zero.");
            }

            Transaction transaction = isDeposit
                    ? new Deposit(account, amount)
                    : new Withdrawal(account, amount);
            transaction.execute();
            account.getTransactionHistory().addTransaction(transaction);

            fileManager.saveAccounts(collectAllAccounts());
            fileManager.saveTransactions(collectAllTransactions());
            refreshBalanceLabel();

            JOptionPane.showMessageDialog(
                    this,
                    (isDeposit ? "Deposited " : "Withdrew ")
                            + String.format("EGP %.2f", amount)
                            + " on account " + account.getAccountNumber(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (isDeposit) {
                depositField.setText("");
            } else {
                withdrawField.setText("");
            }
        } catch (BankException ex) {
            showError(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private ArrayList<Account> collectAllAccounts() {
        ArrayList<Account> all = new ArrayList<>();
        for (User user : AuthService.get().getUsers()) {
            if (user instanceof Client) {
                all.addAll(((Client) user).getAccounts());
            }
        }
        return all;
    }

    private ArrayList<Transaction> collectAllTransactions() {
        ArrayList<Transaction> all = new ArrayList<>();
        for (Account account : collectAllAccounts()) {
            all.addAll(account.getTransactionHistory().getHistory());
        }
        return all;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        accountCombo = new javax.swing.JComboBox<>();
        balanceLabel = new javax.swing.JLabel();
        balanceValueLabel = new javax.swing.JLabel();
        dashboardButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        depositCard = new javax.swing.JPanel();
        depositAccent = new javax.swing.JPanel();
        depositTitle = new javax.swing.JLabel();
        depositAmountLabel = new javax.swing.JLabel();
        depositField = new javax.swing.JTextField();
        depositButton = new javax.swing.JButton();
        withdrawCard = new javax.swing.JPanel();
        withdrawAccent = new javax.swing.JPanel();
        withdrawTitle = new javax.swing.JLabel();
        withdrawAmountLabel = new javax.swing.JLabel();
        withdrawField = new javax.swing.JTextField();
        withdrawButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(248, 250, 252));
        setPreferredSize(new java.awt.Dimension(850, 600));

        headerPanel.setBackground(new java.awt.Color(11, 60, 93));

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Deposit & Withdraw");

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(214, 226, 237));
        subtitleLabel.setText("Choose an account, then move funds safely.");

        accountCombo.setBackground(new java.awt.Color(255, 255, 255));
        accountCombo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        accountCombo.setForeground(new java.awt.Color(0, 0, 0));
        accountCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No accounts" }));

        balanceLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        balanceLabel.setForeground(new java.awt.Color(214, 226, 237));
        balanceLabel.setText("Available Balance");

        balanceValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        balanceValueLabel.setForeground(new java.awt.Color(124, 222, 148));
        balanceValueLabel.setText("EGP 0.00");

        dashboardButton.setBackground(new java.awt.Color(245, 197, 24));
        dashboardButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dashboardButton.setForeground(new java.awt.Color(0, 0, 0));
        dashboardButton.setText("< Dashboard");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subtitleLabel)
                    .addComponent(accountCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                        .addComponent(balanceLabel)
                        .addGap(76, 76, 76))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                        .addComponent(balanceValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)))
                .addComponent(dashboardButton)
                .addGap(22, 22, 22))
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(301, 301, 301)
                .addComponent(titleLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addComponent(subtitleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(accountCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addComponent(balanceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(balanceValueLabel)
                            .addComponent(dashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22))
        );

        contentPanel.setBackground(new java.awt.Color(248, 250, 252));

        depositCard.setBackground(new java.awt.Color(255, 255, 255));
        depositCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(225, 229, 235)));

        depositAccent.setBackground(new java.awt.Color(51, 153, 0));

        javax.swing.GroupLayout depositAccentLayout = new javax.swing.GroupLayout(depositAccent);
        depositAccent.setLayout(depositAccentLayout);
        depositAccentLayout.setHorizontalGroup(
            depositAccentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        depositAccentLayout.setVerticalGroup(
            depositAccentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        depositTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        depositTitle.setForeground(new java.awt.Color(0, 0, 0));
        depositTitle.setText("Deposit");

        depositAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        depositAmountLabel.setForeground(new java.awt.Color(96, 103, 112));
        depositAmountLabel.setText("Amount (EGP)");

        depositField.setBackground(new java.awt.Color(204, 204, 204));
        depositField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        depositField.setForeground(new java.awt.Color(33, 37, 41));

        depositButton.setBackground(new java.awt.Color(51, 153, 0));
        depositButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        depositButton.setForeground(new java.awt.Color(255, 255, 255));
        depositButton.setText("Confirm deposit");

        javax.swing.GroupLayout depositCardLayout = new javax.swing.GroupLayout(depositCard);
        depositCard.setLayout(depositCardLayout);
        depositCardLayout.setHorizontalGroup(
            depositCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(depositAccent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(depositCardLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(depositCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(depositButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(depositField)
                    .addGroup(depositCardLayout.createSequentialGroup()
                        .addGroup(depositCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(depositTitle)
                            .addComponent(depositAmountLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        depositCardLayout.setVerticalGroup(
            depositCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositCardLayout.createSequentialGroup()
                .addComponent(depositAccent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(depositTitle)
                .addGap(26, 26, 26)
                .addComponent(depositAmountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(depositField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(depositButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        withdrawCard.setBackground(new java.awt.Color(255, 255, 255));
        withdrawCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(225, 229, 235)));

        withdrawAccent.setBackground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout withdrawAccentLayout = new javax.swing.GroupLayout(withdrawAccent);
        withdrawAccent.setLayout(withdrawAccentLayout);
        withdrawAccentLayout.setHorizontalGroup(
            withdrawAccentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        withdrawAccentLayout.setVerticalGroup(
            withdrawAccentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        withdrawTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        withdrawTitle.setForeground(new java.awt.Color(0, 0, 0));
        withdrawTitle.setText("Withdraw");

        withdrawAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        withdrawAmountLabel.setForeground(new java.awt.Color(96, 103, 112));
        withdrawAmountLabel.setText("Amount (EGP)");

        withdrawField.setBackground(new java.awt.Color(204, 204, 204));
        withdrawField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        withdrawField.setForeground(new java.awt.Color(33, 37, 41));

        withdrawButton.setBackground(new java.awt.Color(204, 0, 0));
        withdrawButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        withdrawButton.setForeground(new java.awt.Color(255, 255, 255));
        withdrawButton.setText("Confirm withdrawal");

        javax.swing.GroupLayout withdrawCardLayout = new javax.swing.GroupLayout(withdrawCard);
        withdrawCard.setLayout(withdrawCardLayout);
        withdrawCardLayout.setHorizontalGroup(
            withdrawCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(withdrawAccent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(withdrawCardLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(withdrawCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(withdrawButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(withdrawField)
                    .addGroup(withdrawCardLayout.createSequentialGroup()
                        .addGroup(withdrawCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(withdrawTitle)
                            .addComponent(withdrawAmountLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        withdrawCardLayout.setVerticalGroup(
            withdrawCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawCardLayout.createSequentialGroup()
                .addComponent(withdrawAccent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(withdrawTitle)
                .addGap(26, 26, 26)
                .addComponent(withdrawAmountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(withdrawField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(withdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addComponent(depositCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(withdrawCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(depositCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(withdrawCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 69, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> accountCombo;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JLabel balanceValueLabel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton dashboardButton;
    private javax.swing.JPanel depositAccent;
    private javax.swing.JLabel depositAmountLabel;
    private javax.swing.JButton depositButton;
    private javax.swing.JPanel depositCard;
    private javax.swing.JTextField depositField;
    private javax.swing.JLabel depositTitle;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel withdrawAccent;
    private javax.swing.JLabel withdrawAmountLabel;
    private javax.swing.JButton withdrawButton;
    private javax.swing.JPanel withdrawCard;
    private javax.swing.JTextField withdrawField;
    private javax.swing.JLabel withdrawTitle;
    // End of variables declaration//GEN-END:variables
}
