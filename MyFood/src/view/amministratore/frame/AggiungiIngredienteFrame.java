package view.amministratore.frame;

import business.*;
import view.MainFrame;
import view.catalogo.panel.CatalogoPanel;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.TextFieldDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiungiIngredienteFrame extends JFrame {


    private TextFieldDecorator nomeField = new TextFieldDecorator(new JTextField(), 30, 30);

    private JComboBox<String> tipologiaIngrediente = new JComboBox<>();

    private JComboBox<String> distributore = new JComboBox<>();

    private JComboBox<String> produttore = new JComboBox<>();

    private ButtonDecorator confermaButton = new ButtonDecorator(new JButton("Conferma"), 30, 30);


    public AggiungiIngredienteFrame() {
        super("Aggiungi Ingrediente");
        setIconImage(MainFrame.icon.getImage());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        setSize(new Dimension(MainFrame.SCREEN_DIMENSION.width, MainFrame.SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        JPanel ingredientePanel = new JPanel();
        ingredientePanel.setLayout(new BoxLayout(ingredientePanel, BoxLayout.Y_AXIS));

        
        //nome
        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setFont(dialogFont);
        nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientePanel.add(nomeLabel);
        nomeField.setPreferredSize(new Dimension(400, 35));
        nomeField.setMaximumSize(new Dimension(400, 35));
        nomeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientePanel.add(nomeField);
        ingredientePanel.add(Box.createVerticalStrut(10));

        //carico i dati nelle combobox
        fillComboBox();

        //tipologia ingrediente
        JLabel tipologiaLabel = new JLabel("Tipologia");
        tipologiaLabel.setFont(dialogFont);
        tipologiaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientePanel.add(tipologiaLabel);
        tipologiaIngrediente.setAlignmentX(Component.LEFT_ALIGNMENT);
        tipologiaIngrediente.setFont(dialogFont);
        tipologiaIngrediente.setPreferredSize(new Dimension(400, 35));
        tipologiaIngrediente.setMaximumSize(new Dimension(400, 35));
        ingredientePanel.add(tipologiaIngrediente);
        ingredientePanel.add(Box.createVerticalStrut(10));


        //produttore
        JLabel produttoreLabel = new JLabel("Produttore");
        produttoreLabel.setFont(dialogFont);
        produttoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientePanel.add(produttoreLabel);
        produttore.setAlignmentX(Component.LEFT_ALIGNMENT);
        produttore.setFont(dialogFont);
        produttore.setPreferredSize(new Dimension(400, 35));
        produttore.setMaximumSize(new Dimension(400, 35));
        ingredientePanel.add(produttore);
        ingredientePanel.add(Box.createVerticalStrut(10));

        //distributore
        JLabel distributoreLabel = new JLabel("Distributore");
        distributoreLabel.setFont(dialogFont);
        distributoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientePanel.add(distributoreLabel);
        distributore.setAlignmentX(Component.LEFT_ALIGNMENT);
        distributore.setFont(dialogFont);
        distributore.setPreferredSize(new Dimension(400, 35));
        distributore.setMaximumSize(new Dimension(400, 35));
        ingredientePanel.add(distributore);
        ingredientePanel.add(Box.createVerticalStrut(10));


        //conferma

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IngredienteBusiness.addIngrediente(nomeField.getText(), (String)produttore.getSelectedItem(), (String)distributore.getSelectedItem(), (String) tipologiaIngrediente.getSelectedItem());
                JOptionPane.showMessageDialog(null,"Ingrediente aggiunto con successo!","Successo", JOptionPane.INFORMATION_MESSAGE);
                CatalogoPanel.repaintCatalogo();
                dispose();
                }
            });
        add(ingredientePanel, BorderLayout.CENTER);

        JPanel confermaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confermaPanel.add(confermaButton);
        add(confermaPanel, BorderLayout.SOUTH);
    }

    public void fillComboBox() {
        try {
            produttore = new JComboBox<>(EnteBusiness.getProduttoriNames().toArray(new String[0]));
            distributore=new JComboBox<>(EnteBusiness.getDistributoriNames().toArray(new String[0]));
            tipologiaIngrediente=new JComboBox<>(TipologiaBusiness.getTipologieIngredientiNames().toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e); // Lancia l'errore per bloccare ulteriori problemi
        }
    }

}