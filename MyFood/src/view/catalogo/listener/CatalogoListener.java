package view.catalogo.listener;

import business.CatalogoBusiness;
import view.MainFrame;
import view.catalogo.panel.CatalogoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatalogoListener implements ActionListener {

    public CatalogoListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame mainFrame = MainFrame.getInstance();
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();

            if (buttonText.equals("Aggiorna")) {
                String tipoProdotto = CatalogoPanel.getFiltroProdotto();
                String ingrediente = CatalogoPanel.getFiltroIngrediente();
                CatalogoPanel.getInstance().remove(1);
                CatalogoPanel.getInstance().add(CatalogoPanel.fillCatalogoPanel(CatalogoBusiness.loadFilteredProducts(tipoProdotto, ingrediente)), BorderLayout.CENTER);
                mainFrame.revalidate();
                mainFrame.repaint();
            }

        }
        else{
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selectedItem = (String) comboBox.getSelectedItem();
        if(selectedItem.equals("Menu")){
            CatalogoPanel.getInstance().setEmptyIngredienti();
        }
        else{
            CatalogoPanel.getInstance().setEnableIngredienti();
        }
    }
    }

}
