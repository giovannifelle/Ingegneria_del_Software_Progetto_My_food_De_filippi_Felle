package view.prodotto.panel;

import business.ClienteBusiness;
import business.CommentoBusiness;
import business.UserBusiness;
import model.Immagine;
import business.ProdottoBusiness;
import model.prodotto.composite.Prodotto_Singolo;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;
import view.prodotto.listener.ProdottoListener;
import view.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProdottoSingoloPanel extends JMenuBar {

    private ButtonDecorator tornaIndietro = new ButtonDecorator(new JButton("\u2190"), 30, 30);
    private JLabel nomeProdotto = new JLabel();
    private JLabel ingredienti = new JLabel("Ingredienti");
    private JLabel listaIngredienti = new JLabel();
    private JLabel informazioni = new JLabel("Informazioni");
    private JLabel listaInformazioni = new JLabel();
    private ArrayList<ImageIcon> immagini = new ArrayList<>();
    private JLabel lasciaCommento = new JLabel("Lascia un commento");
    private JLabel indiceLabel = new JLabel("Indice di Gradimento");
    private JTextArea commento = new JTextArea();
    private JComboBox<String> indiceGradimento = new JComboBox<>(new String[]{"UNO", "DUE", "TRE", "QUATTRO", "CINQUE"});
    private ButtonDecorator invio = new ButtonDecorator(new JButton("Invia"), 30, 30);
    private JLabel disponibile = new JLabel("Prodotto non disponibile!");
    private ButtonDecorator acquista = new ButtonDecorator(new JButton("Aggiungi al carrello"), 30, 30);

    private ButtonDecorator modifica = new ButtonDecorator(new JButton("Modifica"), 30, 30);

    private ButtonDecorator elimina = new ButtonDecorator(new JButton("Elimina"), 30, 30);

    private JLabel faiLogin = new JLabel("Fai l'accesso per acquistare");

    public ProdottoSingoloPanel(Prodotto_Singolo prodottoSingolo) {
        super();
        setLayout(new BorderLayout());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        fillProdottoInfo(prodottoSingolo);
        listaInformazioni.setFont(dialogFont);
        listaIngredienti.setFont(dialogFont);

        //NORD
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tornaIndietro.addActionListener(new ProdottoListener());
        upperPanel.add(tornaIndietro);
        nomeProdotto.setFont(dialogFont);
        upperPanel.add(nomeProdotto);
        add(upperPanel, BorderLayout.NORTH);

        //CENTRO
        //CENTRO-NORD
        JPanel centerPanel = new JPanel(new BorderLayout());
        PanelDecorator centerNorthPanel = new PanelDecorator(new JPanel(new BorderLayout()), 30, 30);
        JPanel gridPanel = new JPanel(new GridLayout(1, 2));
        JPanel gridPanelListe = new JPanel(new GridLayout(1, 2));
        ingredienti.setFont(dialogFont);
        gridPanel.add(ingredienti);
        informazioni.setFont(dialogFont);
        gridPanel.add(informazioni);

        listaIngredienti.setPreferredSize(null);
        JScrollPane scrollPanel = new JScrollPane(listaIngredienti);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setPreferredSize(new Dimension(200, 100));
        gridPanelListe.add(scrollPanel);

        listaInformazioni.setPreferredSize(null);
        gridPanelListe.add(listaInformazioni);


        centerNorthPanel.add(gridPanel, BorderLayout.NORTH);
        centerNorthPanel.add(gridPanelListe, BorderLayout.CENTER);
        centerPanel.add(centerNorthPanel, BorderLayout.NORTH);


        //CENTRO-CENTRO
        centerPanel.add(fillImmaginiPanel(prodottoSingolo.getImmagini()), BorderLayout.CENTER);

        //CENTRO-SUD
        JPanel centerSouthPanel = new JPanel(new BorderLayout());
        lasciaCommento.setFont(dialogFont);
        centerSouthPanel.add(lasciaCommento, BorderLayout.NORTH);
        commento.setFont(dialogFont);
        commento.setPreferredSize(new Dimension(200, 200));
        JPanel gridComment = new JPanel(new GridLayout(1, 2));
        gridComment.add(commento);

        JPanel inviaPanel = new JPanel();
        inviaPanel.setLayout(new BoxLayout(inviaPanel, BoxLayout.Y_AXIS));
        JPanel indicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        indicePanel.setAlignmentX(LEFT_ALIGNMENT);
        inviaPanel.setAlignmentX(LEFT_ALIGNMENT);
        indiceLabel.setFont(dialogFont);
        indiceGradimento.setFont(dialogFont);
        indicePanel.add(indiceLabel);
        indicePanel.add(indiceGradimento);
        inviaPanel.add(indicePanel);
        invio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentoBusiness.addCommento(commento.getText(), (String) indiceGradimento.getSelectedItem());
                JOptionPane.showMessageDialog(null, "Commento inviato con successo!", "Invio Commento", JOptionPane.INFORMATION_MESSAGE);
                commento.setEditable(false);
                indiceGradimento.setEnabled(false);
                invio.setEnabled(false);
            }
        });
        invio.setAlignmentX(LEFT_ALIGNMENT);
        inviaPanel.add(invio);
        gridComment.add(inviaPanel);
        centerSouthPanel.add(gridComment, BorderLayout.CENTER);
        centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        //SUD
        JPanel acquistaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        faiLogin.setFont(dialogFont);
        acquistaPanel.add(faiLogin);
        disponibile.setFont(dialogFont);
        disponibile.setVisible(false);
        acquistaPanel.add(disponibile);
        acquista.addActionListener(new ProdottoListener());
        acquistaPanel.add(acquista);
        modifica.addActionListener(new ProdottoListener());
        acquistaPanel.add(modifica);
        elimina.addActionListener(new ProdottoListener());
        acquistaPanel.add(elimina);
        switchAtCurrentUserView();
        add(acquistaPanel, BorderLayout.SOUTH);


    }


    public static JScrollPane fillImmaginiPanel(ArrayList<Immagine> listaDiImmagini) {
        JPanel immaginiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (Immagine i : listaDiImmagini) {
            try {
                BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\image\\" + i.getNomeFile()));
                BufferedImage resizedImage = ImageUtils.resizeImage(image, 250, 250);
                JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
                immaginiPanel.add(imageLabel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        JScrollPane scrollPane = new JScrollPane(immaginiPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(250, 250));
        return scrollPane;
    }

    public void fillProdottoInfo(Prodotto_Singolo p) {
        nomeProdotto.setText(p.getNome());
        listaInformazioni.setText(ProdottoBusiness.fillListaInformazioni(p));
        listaIngredienti.setText(ProdottoBusiness.fillListaIngredienti(p));

    }

    public void switchAtCurrentUserView() {
        if (UserBusiness.isAdmin()) {
            invio.setVisible(false);
            commento.setVisible(false);
            lasciaCommento.setVisible(false);
            faiLogin.setVisible(false);
            elimina.setVisible(true);
            modifica.setVisible(true);
            acquista.setVisible(false);
            indiceGradimento.setVisible(false);
            indiceLabel.setVisible(false);
        } else if (UserBusiness.isLogged()) {
            if (ProdottoBusiness.getDisponibilita()) {
                acquista.setVisible(true);
                disponibile.setVisible(false);
            } else {
                acquista.setVisible(false);
                disponibile.setVisible(true);
            }
            if (!ClienteBusiness.acquistato()) {
                commento.setText("Acquista il Prodotto per lasciare una Recensione!");
                commento.setEditable(false);
                indiceGradimento.setEnabled(false);
                invio.setVisible(false);
            } else {
                commento.setEditable(true);
                indiceGradimento.setEnabled(true);
                invio.setVisible(true);
            }
            indiceGradimento.setVisible(true);
            indiceLabel.setVisible(true);
            commento.setVisible(true);
            lasciaCommento.setVisible(true);
            faiLogin.setVisible(false);
            elimina.setVisible(false);
            modifica.setVisible(false);
        } else {
            invio.setVisible(false);
            commento.setVisible(false);
            lasciaCommento.setVisible(false);
            faiLogin.setVisible(true);
            elimina.setVisible(false);
            modifica.setVisible(false);
            acquista.setVisible(false);
            indiceGradimento.setVisible(false);
            indiceLabel.setVisible(false);
        }
    }
}
