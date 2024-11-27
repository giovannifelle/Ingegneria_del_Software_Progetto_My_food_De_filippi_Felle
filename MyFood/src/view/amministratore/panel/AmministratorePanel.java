package view.amministratore.panel;

import model.prodotto.composite.IProdotto;
import view.MainFrame;
import view.amministratore.listener.AmministratoreListener;
import view.catalogo.listener.ChangeCatalogoViewListener;
import view.general.decorator.ButtonDecorator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AmministratorePanel extends JMenuBar {

    private static AmministratorePanel instance;
    private static final JLabel filtra = new JLabel("Cosa vuoi fare?");



    public static AmministratorePanel getInstance() {
        if (instance == null) {
            instance = new AmministratorePanel(); // Crea una nuova istanza se necessario
        }
        return instance;
    }

    private AmministratorePanel() {
        super();
        setLayout(new BorderLayout());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        filtra.setFont(dialogFont);
        labelPanel.add(filtra);
        add(labelPanel, BorderLayout.NORTH);

        add(fillAmministratorePanel(),BorderLayout.CENTER);
    }


    public static JScrollPane fillAmministratorePanel(){
        AmministratoreListener amministratoreListener=new AmministratoreListener();
        JPanel amministratorePanel= new JPanel(new FlowLayout(FlowLayout.CENTER));
        amministratorePanel.setPreferredSize(new Dimension(MainFrame.SCREEN_DIMENSION.width-11, MainFrame.SCREEN_DIMENSION.height));

        ButtonDecorator aggiungiProdotti=new ButtonDecorator(new JButton("<html><div style='text-align: center; '>"+"Aggiungi Prodotto"+"</div></html>"),30,30);
        aggiungiProdotti.setPreferredSize(new Dimension(250,100));
        aggiungiProdotti.addActionListener(amministratoreListener);
        amministratorePanel.add(aggiungiProdotti);

        ButtonDecorator aggiungiMenu=new ButtonDecorator(new JButton("<html><div style='text-align: center; '>"+"Aggiungi Menu"+"</div></html>"),30,30);
        aggiungiMenu.setPreferredSize(new Dimension(250,100));
        aggiungiMenu.addActionListener(amministratoreListener);
        amministratorePanel.add(aggiungiMenu);

        ButtonDecorator aggiungiTipProd=new ButtonDecorator(new JButton("<html><div style='text-align: center; '>"+"Aggiungi Tip. Prodotti/ Ingredienti"+"</div></html>"),30,30);
        aggiungiTipProd.setPreferredSize(new Dimension(250,100));
        aggiungiTipProd.addActionListener(amministratoreListener);
        amministratorePanel.add(aggiungiTipProd);

        ButtonDecorator aggiungiIngredienti=new ButtonDecorator(new JButton("<html><div style='text-align: center; '>"+"Aggiungi Ingrediente"+"</div></html>"),30,30);
        aggiungiIngredienti.setPreferredSize(new Dimension(250,100));
        aggiungiIngredienti.addActionListener(amministratoreListener);
        amministratorePanel.add(aggiungiIngredienti);

        ButtonDecorator prodDist=new ButtonDecorator(new JButton("<html><div style='text-align: center; '>"+"Aggiungi Produttore/ Distributore"+"</div></html>"),30,30);
        prodDist.setPreferredSize(new Dimension(250,100));
        prodDist.addActionListener(amministratoreListener);
        amministratorePanel.add(prodDist);

        ButtonDecorator rispondiCommenti=new ButtonDecorator(new JButton("<html><div style='text-align: center; '>"+"Rispondi ai Commenti"+"</div></html>"),30,30);
        rispondiCommenti.setPreferredSize(new Dimension(250,100));
        rispondiCommenti.addActionListener(amministratoreListener);
        amministratorePanel.add(rispondiCommenti);

        ButtonDecorator gestisciClienti=new ButtonDecorator(new JButton("<html><div style='text-align: center; '>"+"Gestisci Clienti"+"</div></html>"),30,30);
        gestisciClienti.setPreferredSize(new Dimension(250,100));
        gestisciClienti.addActionListener(amministratoreListener);
        amministratorePanel.add(gestisciClienti);

        JScrollPane scrollPane = new JScrollPane(amministratorePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

}
