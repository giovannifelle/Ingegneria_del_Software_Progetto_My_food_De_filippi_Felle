package view.profilo.frame;

import business.CarrelloBusiness;
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

public class OrdiniFrame extends JFrame {

    private static final String stellaPiena = "\u2605";
    private static final String stellaVuota = "\u2606";
    private JComboBox<String> ordini = new JComboBox<>();
    private ButtonDecorator mostraOrdiniButton = new ButtonDecorator(new JButton("Mostra"), 30, 30);
    private ButtonDecorator aggingiCarrelloButton = new ButtonDecorator(new JButton("Aggiungi al Carrello"), 30, 30);
    private ButtonDecorator preferitoButton = new ButtonDecorator(new JButton(""), 30, 30);
    private JLabel infoOrdine = new JLabel();

    private JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private JCheckBox salvatiCheck= new JCheckBox();

    private JLabel salvatiLabel=new JLabel("Preferiti?");

    private Font dialogFont = new Font("Dialog", Font.BOLD, 20);

    public OrdiniFrame() {
        super("I miei ordini");
        setIconImage(MainFrame.icon.getImage());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        setSize(new Dimension(MainFrame.SCREEN_DIMENSION.width, MainFrame.SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        infoOrdine.setText("");
        if (comboBoxPanel.getComponents() != null) {
            comboBoxPanel.removeAll();
        }

        fillComboBox();

        salvatiLabel.setFont(dialogFont);

        OrdiniListener ordiniListener = new OrdiniListener();
        mostraOrdiniButton.addActionListener(ordiniListener);
        aggingiCarrelloButton.addActionListener(ordiniListener);
        preferitoButton.addActionListener(ordiniListener);

        //CHECKLISTENER
        salvatiCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateComboBox();
                revalidate();
                repaint();
            }
        });

        //NORD
        ordini.setFont(dialogFont);
        comboBoxPanel.add(LabelFont.labelWithFont("Ordine: "));
        comboBoxPanel.add(ordini);
        comboBoxPanel.add(mostraOrdiniButton);
        comboBoxPanel.add(salvatiLabel);
        comboBoxPanel.add(salvatiCheck);
        add(comboBoxPanel, BorderLayout.NORTH);

        //CENTER
        infoOrdine.setFont(dialogFont);
        PanelDecorator centerPanel = new PanelDecorator(new JPanel(new BorderLayout()), 30, 30);
        centerPanel.add(infoOrdine, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        //SOUTH
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        preferitoButton.setVisible(false);
        aggingiCarrelloButton.setVisible(false);
        buttonPanel.add(preferitoButton);
        buttonPanel.add(aggingiCarrelloButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    public void fillComboBox() {
        try {
            if(!salvatiCheck.isSelected()){
            ordini = new JComboBox<>(OrdineBusiness.getOrdiniInfo().toArray(new String[0]));}
            else{
                ordini = new JComboBox<>(OrdineBusiness.getOrdiniSalvatiInfo().toArray(new String[0]));
            }
            if (ordini.getItemCount() == 0) {
                ordini.addItem("Nessun Ordine");
                ordini.setEnabled(false);
                mostraOrdiniButton.setEnabled(false);
                aggingiCarrelloButton.setVisible(false);
                preferitoButton.setVisible(false);
            }
            else{
                mostraOrdiniButton.setEnabled(true);
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

    public void updateComboBox() {
        comboBoxPanel.remove(ordini);
        comboBoxPanel.remove(mostraOrdiniButton);
        comboBoxPanel.remove(salvatiLabel);
        comboBoxPanel.remove(salvatiCheck);
        fillComboBox();
        ordini.setFont(dialogFont);
        ordini.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxPanel.add(ordini);
        comboBoxPanel.add(mostraOrdiniButton);
        comboBoxPanel.add(salvatiLabel);
        comboBoxPanel.add(salvatiCheck);
    }

    public void setVisibleButton() {
        aggingiCarrelloButton.setVisible(true);
        preferitoButton.setVisible(true);
    }

    public void setUnvisibleButton() {
        aggingiCarrelloButton.setVisible(false);
        preferitoButton.setVisible(false);
    }

    // Abbiamo optato di implementare questo listener nella classe del Panel
    // poiché non abbiamo una sola instanza di questa classe
    // e porre i bottoni statici avrebbe causato problemi nel passaggio da una schermata all'altra
    private class OrdiniListener implements ActionListener {
        public OrdiniListener() {
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
                    setInfoOrdine(OrdineBusiness.getInfoOrdine(OrdineBusiness.getOrdineFromInfo(currentItem)));
                    if (OrdineBusiness.getOrdineFromInfo(currentItem).getSalvato()) {
                        preferitoButton.setText(stellaPiena);
                    } else {
                        preferitoButton.setText(stellaVuota);
                    }
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    break;
                case stellaPiena:
                    o = OrdineBusiness.getOrdineFromInfo(currentItem);
                    o.setSalvato(false);
                    OrdineBusiness.updateSalvatoOrdine(o);
                    setInfoOrdine(OrdineBusiness.getInfoOrdine(OrdineBusiness.getOrdineFromInfo(currentItem)));
                    preferitoButton.setText(stellaVuota);
                    updateComboBox();
                    break;
                case stellaVuota:
                    o = OrdineBusiness.getOrdineFromInfo(currentItem);
                    o.setSalvato(true);
                    OrdineBusiness.updateSalvatoOrdine(o);
                    setInfoOrdine(OrdineBusiness.getInfoOrdine(OrdineBusiness.getOrdineFromInfo(currentItem)));
                    preferitoButton.setText(stellaPiena);
                    JOptionPane.showMessageDialog(null, "Ordine aggiunto ai preferiti!", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    updateComboBox();

                    break;
                case "Aggiungi al Carrello":
                    o = OrdineBusiness.getOrdineFromInfo(currentItem);
                    CarrelloBusiness.riempiCarrelloDaOrdine(o);
                    JOptionPane.showMessageDialog(null, "Ordine aggiunto al carrello!", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }
}