package view.general.listener;

import business.CarrelloBusiness;
import business.CatalogoBusiness;
import business.LoginBusiness;
import view.*;
import view.access.panel.LogInPanel;
import view.amministratore.panel.AmministratorePanel;
import view.carrello.panel.CarrelloPanel;
import view.catalogo.panel.CatalogoPanel;
import view.general.panel.NavigationPanel;
import view.profilo.panel.ProfiloPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationListener implements ActionListener {

    public NavigationListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NavigationPanel navigationPanel = NavigationPanel.getInstance();
        JButton button = (JButton) e.getSource();
        String view = button.getText();
        if (!view.equals(navigationPanel.getCurrentView())) {
            MainFrame mainFrame = MainFrame.getInstance();
            mainFrame.remove(mainFrame.getContentPane().getComponent(1));
            switch (view) {
                case "Catalogo":
                    mainFrame.add(CatalogoPanel.getInstance(), 1);
                    navigationPanel.setCurrentView("Catalogo");
                    navigationPanel.colorSelectedButton("Catalogo");
                    break;
                case "Accedi":
                    LogInPanel.getInstance().setEmptyTextField();
                    mainFrame.add(LogInPanel.getInstance(), 1);
                    navigationPanel.setCurrentView("Accedi");
                    navigationPanel.colorSelectedButton("Accedi");
                    break;
                case "Profilo":
                    mainFrame.add(new ProfiloPanel(), 1);
                    navigationPanel.setCurrentView("Profilo");
                    navigationPanel.colorSelectedButton("Profilo");
                    break;
                case "Amministratore":
                    mainFrame.add(AmministratorePanel.getInstance(), 1);
                    navigationPanel.setCurrentView("Amministratore");
                    navigationPanel.colorSelectedButton("Amministratore");
                    break;
                case "Esci":
                    CatalogoPanel.getInstance().setEmptyComboBox();
                    CatalogoPanel.getInstance().remove(1);
                    CatalogoPanel.getInstance().add(CatalogoPanel.fillCatalogoPanel(CatalogoBusiness.getProducts()), BorderLayout.CENTER);
                    mainFrame.add(CatalogoPanel.getInstance(), 1);
                    JOptionPane.showMessageDialog(null, "Logout avvenuto con successo!", "Logout", JOptionPane.INFORMATION_MESSAGE);
                    navigationPanel.switchAtCurrentUserView("Guest");
                    LoginBusiness.getInstance().logOut();

                    break;
                case "\uD83D\uDED2":
                    mainFrame.add(new CarrelloPanel(), 1);
                    navigationPanel.setCurrentView("Cliente");
                    navigationPanel.colorSelectedButton("\uD83D\uDED2");
                    break;

            }
            mainFrame.revalidate();
            mainFrame.repaint();
        }

    }
}
