package view.amministratore.frame;

import business.TipologiaBusiness;
import view.MainFrame;
import view.catalogo.panel.CatalogoPanel;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.TextFieldDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiungiTipologiaFrame extends JFrame {


    private TextFieldDecorator nomeField = new TextFieldDecorator(new JTextField(), 30, 30);

    private String[] tipologie = {"Tipologia Prodotto", "Tipologia Ingrediente"};
    private JComboBox<String> tipologia = new JComboBox<>(tipologie);
    private ButtonDecorator confermaButton = new ButtonDecorator(new JButton("Conferma"), 30, 30);


    public AggiungiTipologiaFrame() {
        super("Aggiungi Tipologia Prodotto/Ingrediente");
        setIconImage(MainFrame.icon.getImage());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        setSize(new Dimension(MainFrame.SCREEN_DIMENSION.width, MainFrame.SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        JPanel tipologiaPanel = new JPanel();
        tipologiaPanel.setLayout(new BoxLayout(tipologiaPanel, BoxLayout.Y_AXIS));

        //tipologia
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboBoxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel scegliTipologia = new JLabel("Tipologia ");
        scegliTipologia.setFont(dialogFont);
        scegliTipologia.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBoxPanel.add(scegliTipologia);
        tipologia.setFont(dialogFont);
        tipologia.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBoxPanel.add(tipologia);
        tipologiaPanel.add(comboBoxPanel);
        tipologiaPanel.add(Box.createVerticalStrut(10));

        //nome
        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setFont(dialogFont);
        nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tipologiaPanel.add(nomeLabel);
        nomeField.setPreferredSize(new Dimension(400, 35));
        nomeField.setMaximumSize(new Dimension(400, 35));
        nomeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        tipologiaPanel.add(nomeField);
        tipologiaPanel.add(Box.createVerticalStrut(10));

        //conferma
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tipologia.getSelectedItem().equals("Tipologia Prodotto")) {
                    TipologiaBusiness.addTipologiaProdotto(nomeField.getText());
                    JOptionPane.showMessageDialog(null, "Nuovo tipologia prodotto aggiunta", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    CatalogoPanel.repaintCatalogo();
                    dispose();
                } else {
                    TipologiaBusiness.addTipologiaIngrediente(nomeField.getText());
                    JOptionPane.showMessageDialog(null, "Nuovo tipologia ingrediente aggiunta", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    CatalogoPanel.repaintCatalogo();
                    dispose();
                }
            }
        });

        add(tipologiaPanel, BorderLayout.NORTH);
        JPanel confermaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confermaPanel.add(confermaButton);
        add(confermaPanel, BorderLayout.SOUTH);
    }

}