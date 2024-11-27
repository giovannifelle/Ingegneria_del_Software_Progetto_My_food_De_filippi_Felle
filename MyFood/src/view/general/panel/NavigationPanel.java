package view.general.panel;

import view.MainFrame;
import view.catalogo.panel.CatalogoPanel;
import view.cucina.panel.CucinaPanel;
import view.general.decorator.ButtonDecorator;
import view.general.listener.NavigationListener;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JMenuBar {

    private static NavigationPanel instance = new NavigationPanel();
    private String currentView = "Catalogo";

    private ButtonDecorator carrelloButton = new ButtonDecorator(new JButton("\uD83D\uDED2"), 30, 30);
    private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    private static ButtonDecorator catalogoButton, accediButton, profiloButton, amministratoreButton, esciButton, cucinaButton;

    public static NavigationPanel getInstance() {
        if (instance == null) {
            instance = new NavigationPanel(); // Crea una nuova istanza se necessario
        }
        return instance;
    }


    private NavigationPanel() {
        super();
        setLayout(new BorderLayout());

        catalogoButton = new ButtonDecorator(new JButton("Catalogo"), 30, 30);
        catalogoButton.setColor(ButtonDecorator.colorOrange);
        accediButton = new ButtonDecorator(new JButton("Accedi"), 30, 30);
        profiloButton = new ButtonDecorator(new JButton("Profilo"), 30, 30);
        profiloButton.setVisible(false);
        carrelloButton.setVisible(false);
        esciButton = new ButtonDecorator(new JButton("Esci"), 30, 30);
        esciButton.setVisible(false);
        amministratoreButton = new ButtonDecorator(new JButton("Amministratore"), 30, 30);
        amministratoreButton.setVisible(false);     //invisibile
        cucinaButton = new ButtonDecorator(new JButton("Cucina"), 30, 30);
        cucinaButton.setVisible(false);     //invisibile

        NavigationListener navigationListener = new NavigationListener();
        catalogoButton.addActionListener(navigationListener);
        amministratoreButton.addActionListener(navigationListener);
        profiloButton.addActionListener(navigationListener);
        accediButton.addActionListener(navigationListener);
        esciButton.addActionListener(navigationListener);
        cucinaButton.addActionListener(navigationListener);
        carrelloButton.addActionListener(navigationListener);

        buttonPanel.add(carrelloButton);
        buttonPanel.add(catalogoButton);
        buttonPanel.add(accediButton);
        buttonPanel.add(profiloButton);
        buttonPanel.add(amministratoreButton);
        buttonPanel.add(cucinaButton);
        buttonPanel.add(esciButton);
        add(buttonPanel, BorderLayout.NORTH);

    }

    public String getCurrentView() {
        return currentView;
    }

    public void setCurrentView(String currentView) {
        this.currentView = currentView;
    }

    public void colorSelectedButton(String text) {
        for (Component button : buttonPanel.getComponents()) {
            if (button instanceof ButtonDecorator) {
                if (((ButtonDecorator) button).getText().equals(text)) {
                    ((ButtonDecorator) button).setColor(ButtonDecorator.colorOrange);
                } else {
                    ((ButtonDecorator) button).setColor(Color.LIGHT_GRAY);
                }
            }
        }
    }

    public void switchAtCurrentUserView(String tipo) {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.remove(mainFrame.getContentPane().getComponent(1));
        switch (tipo) {
            case "Cliente":
                mainFrame.add(CatalogoPanel.getInstance(), 1);
                accediButton.setVisible(false);
                profiloButton.setVisible(true);
                esciButton.setVisible(true);
                carrelloButton.setVisible(true);
                currentView = "Catalogo";
                colorSelectedButton("Catalogo");
                break;
            case "Amministratore":
                mainFrame.add(CatalogoPanel.getInstance(), 1);
                accediButton.setVisible(false);
                amministratoreButton.setVisible(true);
                esciButton.setVisible(true);
                carrelloButton.setVisible(false);
                currentView = "Catalogo";
                colorSelectedButton("Catalogo");
                break;
            case "Cucina":
                mainFrame.add(new CucinaPanel(), 1);
                colorSelectedButton("Cucina");
                catalogoButton.setVisible(false);
                accediButton.setVisible(false);
                cucinaButton.setVisible(true);
                carrelloButton.setVisible(false);
                esciButton.setVisible(true);
                break;
            case "Guest":
                mainFrame.add(CatalogoPanel.getInstance(), 1);
                catalogoButton.setVisible(true);
                accediButton.setVisible(true);
                cucinaButton.setVisible(false);
                carrelloButton.setVisible(false);
                amministratoreButton.setVisible(false);
                profiloButton.setVisible(false);
                esciButton.setVisible(false);
                currentView = "Catalogo";
                colorSelectedButton("Catalogo");
                break;
        }
    }
}
