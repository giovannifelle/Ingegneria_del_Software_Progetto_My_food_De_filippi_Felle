package view.profilo.panel;

import business.LoginBusiness;
import model.utente.Cliente;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;
import view.profilo.listener.ProfiloListener;
import view.utils.LabelFont;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProfiloPanel extends JMenuBar {

    private ButtonDecorator cambiaInformazioniButton = new ButtonDecorator(new JButton("Cambia Informazioni"), 30, 30);
    private ButtonDecorator mostraCommentiButton = new ButtonDecorator(new JButton("I miei Commenti"), 30, 30);
    private ButtonDecorator ordiniButton = new ButtonDecorator(new JButton("I miei Ordini"), 30, 30);
    private ButtonDecorator cambiaPasswordButton = new ButtonDecorator(new JButton("Cambia Password"), 30, 30);
    private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    public ProfiloPanel() {
        super();
        setLayout(new BorderLayout());
        Cliente cl = (Cliente) LoginBusiness.getInSessionUser();
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        Font dialogFontit = new Font("Dialog", Font.PLAIN, 20);
        PanelDecorator infoPanel = new PanelDecorator(new JPanel(), 30, 30);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new LineBorder((Color.LIGHT_GRAY), 20));


        infoPanel.add(LabelFont.labelWithFont("Nome"));
        infoPanel.add(LabelFont.labelWithFont(cl.getNome(), dialogFontit));

        infoPanel.add(LabelFont.labelWithFont("Cognome"));
        infoPanel.add(LabelFont.labelWithFont(cl.getCognome(), dialogFontit));

        infoPanel.add(LabelFont.labelWithFont("Email"));
        infoPanel.add(LabelFont.labelWithFont(cl.getEmail(), dialogFontit));

        infoPanel.add(LabelFont.labelWithFont("Residenza"));
        infoPanel.add(LabelFont.labelWithFont(cl.getResidenza(), dialogFontit));

        infoPanel.add(LabelFont.labelWithFont("Professione"));
        infoPanel.add(LabelFont.labelWithFont(cl.getProfessione(), dialogFontit));

        infoPanel.add(LabelFont.labelWithFont("Telefono"));
        infoPanel.add(LabelFont.labelWithFont(cl.getTelefono(), dialogFontit));

        infoPanel.add(LabelFont.labelWithFont("Data di nascita"));
        infoPanel.add(LabelFont.labelWithFont(cl.getDataNascita().toString(), dialogFontit));

        infoPanel.add(LabelFont.labelWithFont("Data di registrazione"));
        infoPanel.add(LabelFont.labelWithFont(cl.getDataRegistrazione().toString(), dialogFontit));

        ProfiloListener profiloListener = new ProfiloListener();
        cambiaPasswordButton.addActionListener(profiloListener);
        cambiaInformazioniButton.addActionListener(profiloListener);
        mostraCommentiButton.addActionListener(profiloListener);
        ordiniButton.addActionListener(new ProfiloListener());
        buttonPanel.add(cambiaInformazioniButton);
        buttonPanel.add(cambiaPasswordButton);
        buttonPanel.add(mostraCommentiButton);
        buttonPanel.add(ordiniButton);

        add(infoPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

}
