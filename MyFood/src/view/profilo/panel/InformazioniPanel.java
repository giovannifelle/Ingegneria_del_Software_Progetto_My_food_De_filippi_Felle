package view.profilo.panel;

import business.LoginBusiness;
import model.utente.Cliente;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;
import view.general.decorator.TextFieldDecorator;
import view.profilo.listener.ProfiloListener;
import business.SessionBusiness;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class InformazioniPanel extends JMenuBar {
    private static InformazioniPanel instance = new InformazioniPanel();


    Cliente cliente= (Cliente) SessionBusiness.getInstance().getFromSession(LoginBusiness.LOGGED_IN_USER);
    private TextFieldDecorator name = new TextFieldDecorator(new JTextField(), 30, 30);
    private TextFieldDecorator surname = new TextFieldDecorator(new JTextField(), 30, 30);

    private TextFieldDecorator residence = new TextFieldDecorator(new JTextField(), 30, 30);

    private TextFieldDecorator profession = new TextFieldDecorator(new JTextField(), 30, 30);

    private TextFieldDecorator phone = new TextFieldDecorator(new JTextField(), 30, 30);

    private TextFieldDecorator email = new TextFieldDecorator(new JTextField(), 30, 30);


    public static InformazioniPanel getInstance() {
        if (instance == null) {
            instance = new InformazioniPanel(); // Crea una nuova istanza se necessario
        }
        return instance;
    }

    private InformazioniPanel() {
        super();
        setLayout(new BorderLayout());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        PanelDecorator formPanel = new PanelDecorator(new JPanel(), 30, 30);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new LineBorder((Color.LIGHT_GRAY), 20));

        JLabel nameLabel = new JLabel("Nome");
        nameLabel.setFont(dialogFont);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        name.setPreferredSize(new Dimension(400, 35));
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel surnameLabel = new JLabel("Cognome");
        surnameLabel.setFont(dialogFont);
        surnameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        surnameLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        surname.setPreferredSize(new Dimension(400, 35));
        surname.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel residenceLabel = new JLabel("Residenza");
        residenceLabel.setFont(dialogFont);
        residenceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        residenceLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));


        residence.setPreferredSize(new Dimension(400, 35));
        residence.setAlignmentX(Component.LEFT_ALIGNMENT);


        JLabel phoneLabel = new JLabel("Telefono");
        phoneLabel.setFont(dialogFont);
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        phoneLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        phone.setPreferredSize(new Dimension(400, 35));
        phone.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel professionLabel = new JLabel("Professione");
        professionLabel.setFont(dialogFont);
        professionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        professionLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        profession.setPreferredSize(new Dimension(400, 35));
        profession.setAlignmentX(Component.LEFT_ALIGNMENT);


        formPanel.add(nameLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(name);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(new JLabel("\n\n"));

        formPanel.add(surnameLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(surname);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(new JLabel("\n\n"));

        formPanel.add(residenceLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(residence);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(new JLabel("\n\n"));

        formPanel.add(phoneLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(phone);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(new JLabel("\n\n"));

        formPanel.add(professionLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(profession);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(new JLabel("\n\n"));

        ButtonDecorator confermaButton = new ButtonDecorator(new JButton("Conferma"), 30, 30);
        confermaButton.addActionListener(new ProfiloListener());
        JPanel gridPanel=new JPanel(new GridLayout(1,3));
        gridPanel.add(new JLabel(""));
        gridPanel.add(formPanel);
        gridPanel.add(new JLabel(""));

        add(gridPanel,BorderLayout.CENTER);
        // Pannello per il pulsante "Conferma"
        JPanel confermaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confermaPanel.add(confermaButton);
        add(confermaPanel, BorderLayout.SOUTH);

    }

    public Cliente getActualCliente(){
            cliente.setNome(name.getText());
            cliente.setCognome(surname.getText());
            cliente.setProfessione(profession.getText());
            cliente.setTelefono(phone.getText());
            cliente.setResidenza(residence.getText());
            return cliente;
    }


    public void setEmptyTextField() {
        name.setText(cliente.getNome());
        surname.setText(cliente.getCognome());
        phone.setText(cliente.getTelefono());
        residence.setText(cliente.getResidenza());
        profession.setText(cliente.getProfessione());
    }
}
