package banking;

import banking.gui.LoginFrame;
import javax.swing.SwingUtilities;
import banking.core.account.Account;
import banking.core.user.Client;
import banking.exception.BankException;
import banking.exception.TransferFailedException;
import banking.persistence.FileManager;
import java.util.ArrayList;
import banking.core.user.User;
import java.util.List;
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello NovaBank");

        SwingUtilities.invokeLater(() -> {

            new LoginFrame().setVisible(true);
        });
        
    

    }
}


