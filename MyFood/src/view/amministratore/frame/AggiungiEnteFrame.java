package view.amministratore.frame;

import business.EnteBusiness;
import view.MainFrame;
import view.catalogo.panel.CatalogoPanel;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.TextFieldDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiungiEnteFrame extends JFrame {


    private TextFieldDecorator nomeField = new TextFieldDecorator(new JTextField(), 30, 30);
    private TextFieldDecorator partitaIVAField = new TextFieldDecorator(new JTextField(), 30, 30);

    private String[] enti = {"Produttore", "Distributore"};
    private JComboBox<String> ente = new JComboBox<>(enti);
    private ButtonDecorator confermaButton = new ButtonDecorator(new JButton("Conferma"), 30, 30);


    public AggiungiEnteFrame() {
        super("Aggiungi Tipologia Prodotto/Ingrediente");
        setIconImage(MainFrame.icon.getImage());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        setSize(new Dimension(MainFrame.SCREEN_DIMENSION.width, MainFrame.SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        JPanel entePanel = new JPanel();
        entePanel.setLayout(new BoxLayout(entePanel, BoxLayout.Y_AXIS));

        //ente
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboBoxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel scegliEnte = new JLabel("Ente ");
        scegliEnte.setFont(dialogFont);
        scegliEnte.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBoxPanel.add(scegliEnte);
        ente.setFont(dialogFont);
        ente.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBoxPanel.add(ente);
        entePanel.add(comboBoxPanel);
        entePanel.add(Box.createVerticalStrut(10));

        //nome
        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setFont(dialogFont);
        nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        entePanel.add(nomeLabel);
        nomeField.setPreferredSize(new Dimension(400, 35));
        nomeField.setMaximumSize(new Dimension(400, 35));
        nomeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        entePanel.add(nomeField);
        entePanel.add(Box.createVerticalStrut(10));

        //partitaIVA
        JLabel partitaIVALabel = new JLabel("Partita IVA");
        partitaIVALabel.setFont(dialogFont);
        partitaIVALabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        entePanel.add(partitaIVALabel);
        partitaIVAField.setPreferredSize(new Dimension(400, 35));
        partitaIVAField.setMaximumSize(new Dimension(400, 35));
        partitaIVAField.setAlignmentX(Component.LEFT_ALIGNMENT);
        entePanel.add(partitaIVAField);
        entePanel.add(Box.createVerticalStrut(10));

        //conferma
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ente.getSelectedItem().equals("Produttore")) {
                    EnteBusiness.addProduttore(nomeField.getText(), partitaIVAField.getText().substring(0,11));
                    JOptionPane.showMessageDialog(null, "Nuovo produtture aggiunto", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    CatalogoPanel.repaintCatalogo();
                    dispose();
                } else {
                    EnteBusiness.addDistributore(nomeField.getText(), partitaIVAField.getText().substring(0,11));
                    JOptionPane.showMessageDialog(null, "Nuovo distributore aggiunto", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    CatalogoPanel.repaintCatalogo();
                    dispose();
                }
            }
        });

        add(entePanel, BorderLayout.NORTH);
        JPanel confermaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confermaPanel.add(confermaButton);
        add(confermaPanel, BorderLayout.SOUTH);
    }

}