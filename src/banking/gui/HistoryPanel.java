/**
 * Online Banking System
 * Course: Programming in Java (25CSCI04C)
 * British University in Egypt (BUE)
 *
 * Phase 2:
 *  @author Ibrahim Mostafa (257511) - Group A-14
 *  @version 2.0
 *  @since 11-4-2026
 */
package banking.gui;

import banking.core.account.Account;
import banking.core.transaction.Transaction;
import banking.core.user.Client;
import banking.core.user.User;
import banking.exception.BankException;
import banking.persistence.FileManager;
import banking.service.AuthService;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Shows every transaction belonging to the logged-in client in a scrollable table.
 * Transactions are loaded from the _transactions.txt file via FileManager.
 */
public class HistoryPanel extends JFrame {

    private final Client currentUser;
    private final FileManager fileManager = new FileManager("data/bank");
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Type", "Amount", "Account", "Status"}, 0);

    public HistoryPanel(Client user) {
        this.currentUser = user;
        setTitle("Nova Bank System - Transaction History");
        setSize(700, 420);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadHistory();
    }

    /** Loads transactions for every account owned by the current client */
    private void loadHistory() {
        try {
            if (currentUser == null) {
                throw new BankException("No user is logged in.");
            }

            // Load every account in the system so FileManager can match IDs,
            // then filter to the ones that belong to the current client.
            ArrayList<User> users = AuthService.get().getUsers();
            ArrayList<Account> allAccounts = fileManager.loadAccounts(users);
            ArrayList<Transaction> transactions = fileManager.loadTransactions(allAccounts);

            ArrayList<Account> mine = currentUser.getAccounts();
            int shown = 0;
            for (Transaction t : transactions) {
                Account acc = t.getAccount();
                if (acc != null && ownedByCurrent(mine, acc.getAccountNumber())) {
                    model.addRow(new Object[]{
                            t.getType(),
                            t.getAmount(),
                            acc.getAccountNumber(),
                            t.getStatus()
                    });
                    shown++;
                }
            }

            if (shown == 0) {
                model.addRow(new Object[]{"—", "—", "—", "No transactions yet"});
            }
        } catch (BankException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** true if the given account number belongs to the current client */
    private boolean ownedByCurrent(ArrayList<Account> mine, String accountNumber) {
        for (Account a : mine) {
            if (a.getAccountNumber().equals(accountNumber)) return true;
        }
        return false;
    }
}
