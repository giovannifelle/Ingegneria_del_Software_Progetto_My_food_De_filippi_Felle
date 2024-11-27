package view.amministratore.listener;

import view.amministratore.frame.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AmministratoreListener implements ActionListener {

    public AmministratoreListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        String text=button.getText().replaceAll("\\<.*?\\>", "");
        switch (text){
            case "Aggiungi Prodotto":
                AggiungiProdottoFrame prodottoFrame =new AggiungiProdottoFrame();
                prodottoFrame.setVisible(true);



                break;
            case "Aggiungi Menu":
                AggiungiMenuFrame menuFrame =new AggiungiMenuFrame();
                menuFrame.setVisible(true);



                break;
            case "Aggiungi Tip. Prodotti/ Ingredienti":
                AggiungiTipologiaFrame tipologiaFrame=new AggiungiTipologiaFrame();
                tipologiaFrame.setVisible(true);
                break;
            case "Aggiungi Ingrediente":
                AggiungiIngredienteFrame ingredienteFrame=new AggiungiIngredienteFrame();
                ingredienteFrame.setVisible(true);
                break;
            case "Aggiungi Produttore/ Distributore":
                AggiungiEnteFrame enteFrame=new AggiungiEnteFrame();
                enteFrame.setVisible(true);

                break;
            case "Rispondi ai Commenti":
                RispondiCommentoFrame rispondiCommentoFrame=new RispondiCommentoFrame();
                rispondiCommentoFrame.setVisible(true);

                break;
            case "Gestisci Clienti":
            GestisciClientiFrame gestisciClientiFrame=new GestisciClientiFrame();
            gestisciClientiFrame.setVisible(true);
            break;
        }

    }
}
