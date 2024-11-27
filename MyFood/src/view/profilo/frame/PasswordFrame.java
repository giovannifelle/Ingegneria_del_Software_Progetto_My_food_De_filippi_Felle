package view.profilo.frame;

import view.MainFrame;
import view.access.panel.SignInPanel;
import view.profilo.panel.PasswordPanel;

import javax.swing.*;
import java.awt.*;

public class PasswordFrame extends JFrame {

    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();


    private static PasswordFrame instance = new PasswordFrame();

    public static PasswordFrame getInstance() {
        if (instance == null) {
            instance = new PasswordFrame(); // Crea una nuova istanza se necessario
        }
        return instance;
    }

    private PasswordFrame() {
        super("Cambia Password");
        setIconImage(MainFrame.icon.getImage());
        setSize(new Dimension(SCREEN_DIMENSION.width, SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        add(new JPanel(), BorderLayout.NORTH);
        add(PasswordPanel.getInstance(), BorderLayout.CENTER);
    }
}
