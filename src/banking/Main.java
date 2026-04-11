package banking;
import banking.gui.LoginFrame;
import javax.swing.SwingUtilities;
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello NovaBank , Hema got here");

        SwingUtilities.invokeLater(() -> {

            new LoginFrame().setVisible(true);

        });

    }
}
