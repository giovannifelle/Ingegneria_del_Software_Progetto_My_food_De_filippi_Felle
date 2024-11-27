package view.prodotto.listener;

import business.*;
import business.result.ProductResult;
import model.prodotto.composite.IProdotto;
import view.MainFrame;
import view.carrello.utils.LoadingFrame;
import view.catalogo.panel.CatalogoPanel;
import view.prodotto.panel.ModificaProdottoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdottoListener implements ActionListener {

    public ProdottoListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame mainFrame = MainFrame.getInstance();
        JButton button = (JButton) e.getSource();
        String text = button.getText();
        switch (text) {
            case "\u2190":
                mainFrame.remove(mainFrame.getContentPane().getComponent(1));
                mainFrame.add(CatalogoPanel.getInstance(), 1);
                SessionBusiness.getInstance().removeFromSession(ProdottoBusiness.PRODOTTO);
                break;
            case "Aggiungi al carrello":
                CarrelloBusiness.addProdotto((IProdotto) SessionBusiness.getInstance().getFromSession(ProdottoBusiness.PRODOTTO));
                JOptionPane.showMessageDialog(null, "Prodotto aggiunto al carrello!", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                //CarrelloBusiness.stampaCarrello();
                break;
            case "Elimina":
                LoadingFrame loadingFrame = new LoadingFrame();
                loadingFrame.setVisible(true);
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    protected ProductResult productResult=new ProductResult();
                    @Override
                    protected Void doInBackground() throws Exception{
                    productResult = AmministratoreBusiness.getInstance().removeProduct(ProdottoBusiness.getProduct());
                    return null;
                }
                @Override
                        protected void done(){

                    switch (productResult.getProductResult())

                    {
                        case REMOVED_PRODUCT:
                            CatalogoPanel.repaintCatalogo();
                            JOptionPane.showMessageDialog(null, productResult.getMessage(), "Successo!", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case ERROR:
                            JOptionPane.showMessageDialog(null, productResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                    loadingFrame.dispose();
                    mainFrame.remove(mainFrame.getContentPane().getComponent(1));
                    mainFrame.add(CatalogoPanel.getInstance(), 1);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    }
                };
                worker.execute();
                break;
            case "Modifica":
                mainFrame.remove(mainFrame.getContentPane().getComponent(1));
                mainFrame.add(new ModificaProdottoPanel(ProdottoBusiness.getProduct()), 1);
                break;
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
