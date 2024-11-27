package view.amministratore.frame;

import business.CommentoBusiness;
import model.Commento;
import view.MainFrame;
import view.general.decorator.ButtonDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RispondiCommentoFrame extends JFrame {
    private JComboBox<String> commenti = new JComboBox<>();

    private ButtonDecorator mostraCommentoButton = new ButtonDecorator(new JButton("Mostra"), 30, 30);
    private ButtonDecorator rispondiButton = new ButtonDecorator(new JButton("Rispondi"), 30, 30);
    private JTextArea risposta = new JTextArea();


    public RispondiCommentoFrame() {
        super("Rispondi Commenti");
        setIconImage(MainFrame.icon.getImage());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        setSize(new Dimension(MainFrame.SCREEN_DIMENSION.width, MainFrame.SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());

        //commenti
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel scegliCommento = new JLabel("Rispondi a: ");
        scegliCommento.setFont(dialogFont);
        scegliCommento.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxPanel.add(scegliCommento);
        fillComboBox();
        commenti.setFont(dialogFont);
        commenti.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxPanel.add(commenti);
        comboBoxPanel.add(mostraCommentoButton);
        add(comboBoxPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();

        mostraCommentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(centerPanel.getComponents().length !=0){
                    centerPanel.removeAll();
                }
                centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
                centerPanel.add(setCommento(CommentoBusiness.getCommentoFromInfo((String) commenti.getSelectedItem())));
                //TextArea risposta
                JLabel rispLabel=new JLabel("Risposta");
                rispLabel.setFont(dialogFont);
                rispLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                centerPanel.add(rispLabel);
                risposta.setFont(dialogFont);
                risposta.setPreferredSize(new Dimension(200, 200));
                risposta.setMaximumSize(new Dimension(800,200));
                risposta.setLineWrap(true);
                risposta.setWrapStyleWord(true);
                centerPanel.add(risposta);
                add(centerPanel, BorderLayout.CENTER);
                rispondiButton.setEnabled(true);
                revalidate();
                repaint();
            }
        });

        //rispondi
        rispondiButton.setEnabled(false);
        rispondiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentoBusiness.rispondiCommento(CommentoBusiness.getCommentoFromInfo((String) commenti.getSelectedItem()), risposta.getText());
                JOptionPane.showMessageDialog(null, "Hai risposto al commento!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                risposta.setText("");


                comboBoxPanel.remove(commenti);
                comboBoxPanel.remove(mostraCommentoButton);
                fillComboBox();
                commenti.setFont(dialogFont);
                commenti.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboBoxPanel.add(commenti);
                comboBoxPanel.add(mostraCommentoButton);

                remove(centerPanel);
                rispondiButton.setEnabled(false);
                revalidate();
                repaint();
            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(rispondiButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void fillComboBox() {
        try {
            commenti = new JComboBox<>(CommentoBusiness.getCommentiSenzaRispostaInfo().toArray(new String[0]));
            if(commenti.getItemCount()==0){
                commenti.addItem("Nessun commento a cui rispondere");
                commenti.setEnabled(false);
                mostraCommentoButton.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e); // Lancia l'errore per bloccare ulteriori problemi
        }
    }

    public JPanel setCommento(Commento c) {
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        JPanel commentoPanel = new JPanel();
        JTextArea commento = new JTextArea();
        commento.setEditable(false);
        commento.setLineWrap(true);
        commento.setWrapStyleWord(true);
        commento.setPreferredSize(new Dimension(800,300));
        commento.setMaximumSize(new Dimension(800,300));
        commento.setFont(dialogFont);
        commento.setText("Commento di: " + c.getCliente().getEmail() +
                "\nProdotto: " + c.getProdotto().getNome() +
                "\nIndice di Gradimento: " + c.getIndiceGradimento() +"\n"+
                "\n" + c.getTesto());
        commentoPanel.add(commento);
        return commentoPanel;
    }

}