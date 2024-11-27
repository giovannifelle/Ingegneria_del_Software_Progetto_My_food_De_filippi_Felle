package view.carrello.panel;

import business.CarrelloBusiness;
import model.prodotto.composite.IProdotto;
import view.MainFrame;
import view.carrello.listener.CarrelloListener;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarrelloPanel extends JMenuBar {

    private PanelDecorator carrelloPanel = new PanelDecorator(new JPanel(), 30, 30);

    private JLabel prezzoTotaleLabel = new JLabel("Prezzo totale: ");

    private JLabel prezzoTotaleNumLabel = new JLabel();

    private ButtonDecorator pagareButton = new ButtonDecorator(new JButton("Conferma Ordine"), 30, 30);

    private Font dialogFont = new Font("Dialog", Font.BOLD, 20);

    public CarrelloPanel() {
        super();
        setLayout(new BorderLayout());

        JPanel borderPanel = new JPanel(new BorderLayout());
        carrelloPanel.setLayout(new BoxLayout(carrelloPanel, BoxLayout.Y_AXIS));
        carrelloPanel.setBorder(new LineBorder((Color.LIGHT_GRAY), 20));
        JScrollPane scrollPanel = new JScrollPane(carrelloPanel);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        prezzoTotaleLabel.setFont(dialogFont);
        prezzoTotaleNumLabel.setFont(dialogFont);


        fillFormPanel();

        southPanel.add(prezzoTotaleLabel);
        southPanel.add(prezzoTotaleNumLabel);
        southPanel.add(Box.createHorizontalStrut(40));
        pagareButton.addActionListener(new CarrelloListener());
        southPanel.add(pagareButton);
        borderPanel.add(scrollPanel, BorderLayout.CENTER);
        borderPanel.add(southPanel, BorderLayout.SOUTH);

        add(borderPanel);
    }

    private void fillFormPanel() {

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setMaximumSize(new Dimension(MainFrame.SCREEN_DIMENSION.width - 20, 80));
        labelPanel.setBackground(Color.WHITE);


        JLabel nomeLabel = new JLabel("Nome");
        JLabel prezzoLabel = new JLabel("Prezzo");


        nomeLabel.setFont(dialogFont);
        nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        prezzoLabel.setFont(dialogFont);
        prezzoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel eliminaLabel = new JLabel("Elimina");
        eliminaLabel.setFont(dialogFont);
        eliminaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelPanel.add(Box.createHorizontalStrut(100));
        labelPanel.add(nomeLabel);
        labelPanel.add(Box.createHorizontalStrut(190));

        labelPanel.add(prezzoLabel);
        labelPanel.add(Box.createHorizontalStrut(MainFrame.SCREEN_DIMENSION.width - 590));

        labelPanel.add(eliminaLabel);

        carrelloPanel.add(labelPanel);

        if (!CarrelloBusiness.isNull())
            for (IProdotto p : CarrelloBusiness.getCarrello()) {
                JPanel prodottoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                prodottoPanel.setMaximumSize(new Dimension(MainFrame.SCREEN_DIMENSION.width - 20, 80));
                prodottoPanel.setBackground(Color.WHITE);

                JButton nomeProdottoLabel = new JButton("<html><div style='text-align: center; '>" + p.getNome() + "</div></html>");
                nomeProdottoLabel.setPreferredSize(new Dimension(250, 60));
                nomeProdottoLabel.setBorderPainted(false);
                nomeProdottoLabel.setBorder(null);
                nomeProdottoLabel.setContentAreaFilled(false);
                nomeProdottoLabel.setOpaque(false);
                JButton prezzoProdottoLabel = new JButton("<html><div style='text-align: center; '>" + p.getPrezzo().toString() +" €" + "</div></html>");
                prezzoProdottoLabel.setPreferredSize(new Dimension(150, 60));
                prezzoProdottoLabel.setOpaque(false);
                prezzoProdottoLabel.setBorder(null);
                prezzoProdottoLabel.setBorderPainted(false);
                prezzoProdottoLabel.setContentAreaFilled(false);


                nomeProdottoLabel.setFont(dialogFont);
                nomeProdottoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                prezzoProdottoLabel.setFont(dialogFont);
                prezzoProdottoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


                ButtonDecorator cestinoButton = new ButtonDecorator(new JButton("\uD83D\uDDD1"), 30, 30);

                prodottoPanel.add(nomeProdottoLabel);
                prodottoPanel.add(Box.createHorizontalStrut(60));

                prodottoPanel.add(prezzoProdottoLabel);
                prodottoPanel.add(Box.createHorizontalStrut(MainFrame.SCREEN_DIMENSION.width - 610));


                cestinoButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Rimuovi il pannello contenente il bottone premuto
                        carrelloPanel.remove(prodottoPanel);
                        CarrelloBusiness.removeProdotto(p);
                        prezzoTotaleNumLabel.setText(CarrelloBusiness.prezzoFinale().toString());
                        if(CarrelloBusiness.getCarrello().isEmpty()){
                            pagareButton.setEnabled(false);
                        }
                        carrelloPanel.revalidate();
                        carrelloPanel.repaint();
                    }
                });


                prodottoPanel.add(cestinoButton);

                carrelloPanel.add(prodottoPanel);

                prezzoTotaleNumLabel.setText(CarrelloBusiness.prezzoFinale().toString()+" €");
                pagareButton.setEnabled(true);
                revalidate();
                repaint();
            }
        else {
            pagareButton.setEnabled(false);
            prezzoTotaleNumLabel.setText("0.0");
        }


    }

}
