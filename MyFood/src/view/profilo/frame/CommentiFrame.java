package view.profilo.frame;

import business.CommentoBusiness;
import model.Commento;
import view.MainFrame;
import view.general.decorator.ButtonDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentiFrame extends JFrame {
    private JComboBox<String> commenti = new JComboBox<>();
    private ButtonDecorator mostraCommentoButton = new ButtonDecorator(new JButton("Mostra"), 30, 30);
    //private ButtonDecorator rispondiButton = new ButtonDecorator(new JButton("Rispondi"), 30, 30);
    //private JTextArea risposta = new JTextArea();


    public CommentiFrame() {
        super("I miei commenti");
        setIconImage(MainFrame.icon.getImage());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        setSize(new Dimension(MainFrame.SCREEN_DIMENSION.width, MainFrame.SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());

        //commenti
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel scegliCommento = new JLabel("Il mio commento: ");
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

                if (centerPanel.getComponents().length != 0) {
                    centerPanel.removeAll();
                }
                centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
                centerPanel.add(setCommento(CommentoBusiness.getCommentoFromInfo((String) commenti.getSelectedItem())));
                //TextArea risposta
               /* JLabel rispLabel=new JLabel("Risposta");
                rispLabel.setFont(dialogFont);
                rispLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                centerPanel.add(rispLabel);*/
               /* risposta.setFont(dialogFont);
                risposta.setPreferredSize(new Dimension(200, 200));
                risposta.setMaximumSize(new Dimension(800,200));
                risposta.setLineWrap(true);
                risposta.setWrapStyleWord(true);
                centerPanel.add(risposta);*/
                add(centerPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });


    }

    public void fillComboBox() {
        try {
            commenti = new JComboBox<>(CommentoBusiness.getCommentiInfoOfCliente().toArray(new String[0]));
            if (commenti.getItemCount() == 0) {
                commenti.addItem("Nessun commento");
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
        commentoPanel.setLayout(new BoxLayout(commentoPanel, BoxLayout.Y_AXIS));
        JTextArea commento = new JTextArea();
        commento.setEditable(false);
        commento.setLineWrap(true);
        commento.setWrapStyleWord(true);
        commento.setPreferredSize(new Dimension(800, 300));
        commento.setMaximumSize(new Dimension(800, 300));
        commento.setFont(dialogFont);
        commento.setText("Commento di: " + c.getCliente().getEmail() +
                "\nProdotto: " + c.getProdotto().getNome() +
                "\nIndice di Gradimento: " + c.getIndiceGradimento() + "\n" +
                "\n" + c.getTesto());
        commentoPanel.add(commento);
        JLabel rispLabel = new JLabel("Risposta");
        rispLabel.setFont(dialogFont);
        rispLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentoPanel.add(rispLabel);
        JTextArea risposta = new JTextArea();
        risposta.setEditable(false);
        risposta.setLineWrap(true);
        risposta.setWrapStyleWord(true);
        risposta.setPreferredSize(new Dimension(800, 300));
        risposta.setMaximumSize(new Dimension(800, 300));
        risposta.setFont(dialogFont);
        risposta.setText(c.getRisposta());
        commentoPanel.add(risposta);
        return commentoPanel;
    }

}