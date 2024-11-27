package view.profilo.frame;

import view.MainFrame;
import view.profilo.panel.InformazioniPanel;
import view.profilo.panel.PasswordPanel;

import javax.swing.*;
import java.awt.*;

public class InformazioniFrame extends JFrame {

    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();


    private static InformazioniFrame instance = new InformazioniFrame();

    public static InformazioniFrame getInstance() {
        if (instance == null) {
            instance = new InformazioniFrame(); // Crea una nuova istanza se necessario
        }
        return instance;
    }

    private InformazioniFrame() {
        super("Cambia Informazioni");
        setIconImage(MainFrame.icon.getImage());
        setSize(new Dimension(SCREEN_DIMENSION.width, SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        add(new JPanel(), BorderLayout.NORTH);
        add(InformazioniPanel.getInstance(), BorderLayout.CENTER);
    }
}
