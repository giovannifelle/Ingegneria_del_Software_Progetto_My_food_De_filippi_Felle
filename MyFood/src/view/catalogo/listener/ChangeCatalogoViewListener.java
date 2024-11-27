package view.catalogo.listener;

import business.ProdottoBusiness;
import model.prodotto.composite.IProdotto;
import model.prodotto.composite.Menu;
import model.prodotto.composite.Prodotto_Singolo;
import view.MainFrame;
import view.prodotto.panel.MenuPanel;
import view.prodotto.panel.ProdottoSingoloPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeCatalogoViewListener implements ActionListener {

    public ChangeCatalogoViewListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MainFrame mainFrame = MainFrame.getInstance();
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();
            ProdottoBusiness.loadProduct(buttonText);
            mainFrame.remove(mainFrame.getContentPane().getComponent(1));

            IProdotto p= ProdottoBusiness.getProduct();
            if(p instanceof Prodotto_Singolo) {
                mainFrame.add(new ProdottoSingoloPanel((Prodotto_Singolo) p), 1);
            }
            else if(p instanceof Menu){
                mainFrame.add(new MenuPanel((Menu)p),1);

            }
            mainFrame.revalidate();
            mainFrame.repaint();

        }
}