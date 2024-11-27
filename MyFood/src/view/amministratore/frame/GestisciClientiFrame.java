package view.amministratore.frame;

import business.ClienteBusiness;
import view.MainFrame;
import view.general.decorator.ButtonDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestisciClientiFrame extends JFrame {
    private JComboBox<String> clienti = new JComboBox<>();
    private ButtonDecorator eliminaButton = new ButtonDecorator(new JButton("Elimina"), 30, 30);
    private ButtonDecorator disabilitaAbilitaButton = new ButtonDecorator(new JButton("Disabilita/Abilita"), 30, 30);


    public GestisciClientiFrame() {
        super("Gestisci Clienti");
        setIconImage(MainFrame.icon.getImage());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);

        setSize(new Dimension(MainFrame.SCREEN_DIMENSION.width, MainFrame.SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        JPanel clientiPanel = new JPanel();
        clientiPanel.setLayout(new BoxLayout(clientiPanel, BoxLayout.Y_AXIS));

        //clienti
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel scegliTipologia = new JLabel("Cliente ");
        scegliTipologia.setFont(dialogFont);
        scegliTipologia.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxPanel.add(scegliTipologia);
        fillComboBox();
        clienti.setFont(dialogFont);
        clienti.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxPanel.add(clienti);
        clientiPanel.add(comboBoxPanel);
        clientiPanel.add(Box.createVerticalStrut(10));

        //elimina
        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteBusiness.removeCliente((String) clienti.getSelectedItem());
                JOptionPane.showMessageDialog(null, "Cliente rimosso con successo!","Successo",JOptionPane.INFORMATION_MESSAGE);
                comboBoxPanel.remove(clienti);
                fillComboBox();
                clienti.setFont(dialogFont);
                clienti.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboBoxPanel.add(clienti);
                revalidate();
                repaint();


            }
        });

        //disabilita
        disabilitaAbilitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteBusiness.disabilitaAbilitaCliente((String) clienti.getSelectedItem());
                JOptionPane.showMessageDialog(null, "Stato del cliente aggiornato con successo!","Successo",JOptionPane.INFORMATION_MESSAGE);
                comboBoxPanel.remove(clienti);
                fillComboBox();
                clienti.setFont(dialogFont);
                clienti.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboBoxPanel.add(clienti);
                revalidate();
                repaint();
            }
        });

        add(clientiPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(eliminaButton);
        buttonPanel.add(disabilitaAbilitaButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void fillComboBox() {
        try {
            clienti = new JComboBox<>(ClienteBusiness.getClientiInfo().toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e); // Lancia l'errore per bloccare ulteriori problemi
        }
    }

}