package view;
import model.prodotto.composite.IProdotto;
import view.catalogo.panel.CatalogoPanel;
import view.general.panel.NavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

    public static final ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\image\\logo\\logo.jpg");

    private static MainFrame instance = new MainFrame();

    public static MainFrame getInstance() {
        return instance;
    }

    private MainFrame() {
        super("MyFood");
        setSize(new Dimension(SCREEN_DIMENSION.width, SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(NavigationPanel.getInstance(), BorderLayout.NORTH);
        ArrayList<IProdotto> prodottos=new ArrayList<IProdotto>();
        add(CatalogoPanel.getInstance(), BorderLayout.CENTER);
        setIconImage(MainFrame.icon.getImage());

    }

}
