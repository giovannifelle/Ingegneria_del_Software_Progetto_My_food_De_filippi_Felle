package view.access.frame;

import view.MainFrame;
import view.access.panel.SignInPanel;

import javax.swing.*;
import java.awt.*;

public class SignInFrame extends JFrame {

    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();


    private static SignInFrame instance = new SignInFrame();

    public static SignInFrame getInstance() {
        if (instance == null) {
            instance = new SignInFrame(); // Crea una nuova istanza se necessario
        }
        return instance;
    }

    private SignInFrame() {
        super("Registrati");
        setIconImage(MainFrame.icon.getImage());
        setSize(new Dimension(SCREEN_DIMENSION.width, SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        add(new JPanel(), BorderLayout.NORTH);
        add(SignInPanel.getInstance(), BorderLayout.CENTER);
    }
}
