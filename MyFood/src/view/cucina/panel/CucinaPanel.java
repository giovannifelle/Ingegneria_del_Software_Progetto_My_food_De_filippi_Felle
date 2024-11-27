package view.cucina.panel;

import business.OrdineBusiness;
import model.Ordine;
import view.MainFrame;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;
import view.utils.LabelFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CucinaPanel extends JMenuBar {
    private JComboBox<String> ordini = new JComboBox<>();
    private ButtonDecorator mostraOrdiniButton = new ButtonDecorator(new JButton("Mostra"), 30, 30);
    private ButtonDecorator cambiaStatoButton = new ButtonDecorator(new JButton(""), 30, 30);
    private ButtonDecorator annullaButton = new ButtonDecorator(new JButton("Annullato"), 30, 30);
    private JLabel infoOrdine = new JLabel();

    private JLabel cambiaStatoLabel = new JLabel("Cambia stato in: ");

    private JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private Font dialogFont = new Font("Dialog", Font.BOLD, 20);

    public CucinaPanel() {
        super();
        setLayout(new BorderLayout());
        infoOrdine.setText("");
        if (comboBoxPanel.getComponents() != null) {
            comboBoxPanel.removeAll();
        }
        cambiaStatoButton.setText("");
        fillComboBox();

        CucinaListener cucinaListener = new CucinaListener();
        mostraOrdiniButton.addActionListener(cucinaListener);
        cambiaStatoButton.addActionListener(cucinaListener);
        annullaButton.addActionListener(cucinaListener);
        //NORD
        ordini.setFont(dialogFont);
        comboBoxPanel.add(LabelFont.labelWithFont("Ordine: "));
        comboBoxPanel.add(ordini);
        comboBoxPanel.add(mostraOrdiniButton);
        add(comboBoxPanel, BorderLayout.NORTH);

        //CENTER
        infoOrdine.setFont(dialogFont);
        PanelDecorator centerPanel = new PanelDecorator(new JPanel(new BorderLayout()), 30, 30);
        centerPanel.add(infoOrdine, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        //SOUTH
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cambiaStatoLabel.setFont(dialogFont);
        cambiaStatoLabel.setVisible(false);
        buttonPanel.add(cambiaStatoLabel);
        cambiaStatoButton.setVisible(false);
        annullaButton.setVisible(false);
        buttonPanel.add(cambiaStatoButton);
        buttonPanel.add(annullaButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    public void fillComboBox() {
        try {
            ordini = new JComboBox<>(OrdineBusiness.getOrdiniIncompletiCucinaInfo().toArray(new String[0]));
            if (ordini.getItemCount() == 0) {
                ordini.addItem("Nessun Ordine");
                ordini.setEnabled(false);
                mostraOrdiniButton.setEnabled(false);
                cambiaStatoButton.setVisible(false);
                annullaButton.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e); // Lancia l'errore per bloccare ulteriori problemi
        }
    }

    public JLabel getInfoOrdine() {
        return infoOrdine;
    }

    public void setInfoOrdine(String testo) {
        infoOrdine.setText(testo);
    }

    public String getOrdiniSelectedItem() {
        return (String) ordini.getSelectedItem();
    }

    public void setCambiaStatoButtonLabel(String label) {
        cambiaStatoButton.setText(label);
    }

    public void updateComboBox() {
        comboBoxPanel.remove(ordini);
        comboBoxPanel.remove(mostraOrdiniButton);
        fillComboBox();
        ordini.setFont(dialogFont);
        ordini.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxPanel.add(ordini);
        comboBoxPanel.add(mostraOrdiniButton);
    }

    public void setVisibleButton() {
        cambiaStatoButton.setVisible(true);
        annullaButton.setVisible(true);
        cambiaStatoLabel.setVisible(true);
    }

    public void setUnvisibleButton() {
        cambiaStatoButton.setVisible(false);
        annullaButton.setVisible(false);
        cambiaStatoLabel.setVisible(false);
    }

    //Abbiamo optato di implementare questo listener nella classe del Panel
    // poiché non abbiamo una sola instanza di questa classe
    // e porre i bottoni statici avrebbe causato problemi nel passaggio da una schermata all'altra
    private class CucinaListener implements ActionListener {
        public CucinaListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MainFrame mainFrame = MainFrame.getInstance();
            JButton button = (JButton) e.getSource();
            String text = button.getText();
            String currentItem = getOrdiniSelectedItem();
            Ordine o = new Ordine();
            switch (text) {
                case "Mostra":
                    setVisibleButton();
                    setInfoOrdine(OrdineBusiness.getInfoOrdine(OrdineBusiness.getOrdineCucinaFromInfo(currentItem)));
                    if (OrdineBusiness.getOrdineCucinaFromInfo(currentItem).getStato().equals(Ordine.STATO.NON_PAGATO)) {
                        setCambiaStatoButtonLabel("In Lavorazione");
                    } else if (OrdineBusiness.getOrdineCucinaFromInfo(currentItem).getStato().equals(Ordine.STATO.IN_LAVORAZIONE)) {
                        setCambiaStatoButtonLabel("Pronto");
                    }
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    break;
                case "In Lavorazione":
                    o = OrdineBusiness.getOrdineCucinaFromInfo(currentItem);
                    o.setStato(Ordine.STATO.IN_LAVORAZIONE);
                    OrdineBusiness.updateStatoOrdine(o);
                    updateComboBox();
                    setInfoOrdine(OrdineBusiness.getInfoOrdine(OrdineBusiness.getOrdineCucinaFromInfo(currentItem)));
                    setCambiaStatoButtonLabel("Pronto");
                    break;
                case "Pronto":
                    o = OrdineBusiness.getOrdineCucinaFromInfo(currentItem);
                    o.setStato(Ordine.STATO.PRONTO);
                    OrdineBusiness.updateStatoOrdine(o);
                    OrdineBusiness.inviaMailOrdinePronto(o);
                    updateComboBox();
                    setInfoOrdine("");
                    setUnvisibleButton();
                    break;
                case "Annullato":
                    o = OrdineBusiness.getOrdineCucinaFromInfo(currentItem);
                    o.setStato(Ordine.STATO.ANNULLATO);
                    OrdineBusiness.updateStatoOrdine(o);
                    updateComboBox();
                    setInfoOrdine("");
                    setUnvisibleButton();
                    break;
            }
            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }
}