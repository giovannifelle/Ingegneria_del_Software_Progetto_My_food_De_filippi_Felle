package view.profilo.listener;

import business.UserBusiness;
import business.result.UserResult;
import view.MainFrame;
import view.profilo.frame.InformazioniFrame;
import view.profilo.frame.OrdiniFrame;
import view.profilo.panel.InformazioniPanel;
import view.profilo.frame.PasswordFrame;
import view.profilo.frame.CommentiFrame;
import view.profilo.panel.PasswordPanel;
import view.profilo.panel.ProfiloPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfiloListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame mainFrame = MainFrame.getInstance();
        PasswordPanel passwordPanel = PasswordPanel.getInstance();
        InformazioniPanel informazioniPanel = InformazioniPanel.getInstance();
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();
        PasswordFrame passwordFrame = PasswordFrame.getInstance();
        CommentiFrame commentiFrame = new CommentiFrame();
        OrdiniFrame ordiniFrame = new OrdiniFrame();
        InformazioniFrame informazioniFrame = InformazioniFrame.getInstance();
        switch (buttonText) {
            case "Cambia Informazioni":
                InformazioniPanel.getInstance().setEmptyTextField();
                informazioniFrame.setVisible(true);
                break;

            case "Cambia Password":
                PasswordPanel.getInstance().setEmptyTextField();
                passwordFrame.setVisible(true);
                break;
            case "I miei Commenti":
                commentiFrame.setVisible(true);
                break;
            case "I miei Ordini":
                ordiniFrame.setVisible(true);
                break;
            case "Conferma":
                if (passwordFrame.isVisible()) {
                    UserBusiness userBusiness = UserBusiness.getInstance();
                    UserResult userResult = userBusiness.changePassword(passwordPanel.getOldPassword(), passwordPanel.getNewPassword());
                    switch (userResult.getUserResult()) {
                        case UPDATED_PASSWORD:
                            JOptionPane.showMessageDialog(null, userResult.getMessage(), "Password Aggiornata", JOptionPane.INFORMATION_MESSAGE);
                            passwordFrame.dispose();
                            break;
                        case WRONG_PASSWORD:
                            JOptionPane.showMessageDialog(null, userResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            break;
                        case OLD_PASSWORD:
                            JOptionPane.showMessageDialog(null, userResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            break;
                        case EMPTY_NEW_PASSWORD:
                            JOptionPane.showMessageDialog(null, userResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                    break;
                }
                if (informazioniFrame.isVisible()) {
                    UserBusiness userBusiness = UserBusiness.getInstance();
                    UserResult userResult = userBusiness.changeInfo(informazioniPanel.getActualCliente());
                    switch (userResult.getUserResult()) {
                        case UPDATED_INFO:
                            JOptionPane.showMessageDialog(null, userResult.getMessage(), "Informazioni Aggiornate", JOptionPane.INFORMATION_MESSAGE);
                            informazioniFrame.dispose();
                            break;
                        case NOT_ENOUGH_INFO:
                            JOptionPane.showMessageDialog(null, userResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                    break;

                }
        }//Aggiorno il pannello del profilo rimuovendolo e aggiungendone un altro poiché non ha il singleton per motivi di gestione dati
        mainFrame.remove(mainFrame.getContentPane().getComponent(1));
        mainFrame.add(new ProfiloPanel(), 1);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
