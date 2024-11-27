package view.carrello.listener;

import business.CarrelloBusiness;
import business.CucinaBusiness;
import business.OrdineBusiness;
import view.MainFrame;
import view.catalogo.panel.CatalogoPanel;
import view.general.panel.NavigationPanel;
import view.carrello.utils.LoadingFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarrelloListener implements ActionListener {

    private JComboBox<String> sediCucine = new JComboBox<>();

    @Override
    public void actionPerformed(ActionEvent e) {

        sediCucine = new JComboBox<>(CucinaBusiness.getSedi().toArray(new String[0]));

        MainFrame mainFrame = MainFrame.getInstance();
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();
        switch (buttonText) {
            case "Conferma Ordine":
                int result = JOptionPane.showConfirmDialog(null, sediCucine, "Seleziona una sede", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String selectedOption = (String) sediCucine.getSelectedItem();
                    LoadingFrame loadingCarrelloFrame = new LoadingFrame();
                    loadingCarrelloFrame.setVisible(true);
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            OrdineBusiness.addOrdine(CucinaBusiness.getCucinaBySede(selectedOption));
                            OrdineBusiness.inviaMailScontrino();
                            return null;
                        }

                        @Override
                        protected void done() {
                            loadingCarrelloFrame.dispose();
                            JOptionPane.showMessageDialog(null, "Dirigiti in cassa per pagare", "Ordine Confermato", JOptionPane.INFORMATION_MESSAGE);
                            CarrelloBusiness.svuotaCarrello();
                            mainFrame.remove(mainFrame.getContentPane().getComponent(1));
                            mainFrame.add(CatalogoPanel.getInstance(), 1);
                            NavigationPanel.getInstance().setCurrentView("Catalogo");
                            NavigationPanel.getInstance().colorSelectedButton("Catalogo");
                            mainFrame.revalidate();
                            mainFrame.repaint();
                        }
                    };
                    worker.execute();
                }
                    break;
                }
        }
    }
