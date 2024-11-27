package view.access.panel;

import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;
import view.general.decorator.PasswordFieldDecorator;
import view.general.decorator.TextFieldDecorator;
import view.access.listener.SignInListener;
import view.utils.DateComboBox;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.LocalDate;

public class SignInPanel extends JMenuBar {

    private static SignInPanel instance = new SignInPanel();

    private TextFieldDecorator name = new TextFieldDecorator(new JTextField(), 30, 30);
    private TextFieldDecorator surname = new TextFieldDecorator(new JTextField(), 30, 30);

    private TextFieldDecorator residence = new TextFieldDecorator(new JTextField(), 30, 30);

    private TextFieldDecorator profession = new TextFieldDecorator(new JTextField(), 30, 30);

    private TextFieldDecorator phone = new TextFieldDecorator(new JTextField(), 30, 30);

    private TextFieldDecorator email = new TextFieldDecorator(new JTextField(), 30, 30);

    private PasswordFieldDecorator password = new PasswordFieldDecorator(new JPasswordField(), 30, 30);

    private DateComboBox dateComboBox = new DateComboBox(30, 30);

    public static SignInPanel getInstance() {
        if (instance == null) {
            instance = new SignInPanel(); // Crea una nuova istanza se necessario
        }
        return instance;
    }

    private SignInPanel() {
        super();
        setLayout(new BorderLayout());
        Font dialogFont = new Font("Dialog", Font.BOLD, 15);
        //Formpanel
        PanelDecorator formPanel = new PanelDecorator(new JPanel(), 30, 30);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new LineBorder((Color.LIGHT_GRAY), 20));

        JLabel nameLabel = new JLabel("Nome*");
        nameLabel.setFont(dialogFont);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        name.setPreferredSize(new Dimension(400, 35));
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel surnameLabel = new JLabel("Cognome*");
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

        JLabel professionLabel = new JLabel("Professione");
        professionLabel.setFont(dialogFont);
        professionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        professionLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        profession.setPreferredSize(new Dimension(400, 35));
        profession.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel phoneLabel = new JLabel("Telefono");
        phoneLabel.setFont(dialogFont);
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        phoneLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        phone.setPreferredSize(new Dimension(400, 35));
        phone.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = new JLabel("Email*");
        emailLabel.setFont(dialogFont);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        email.setPreferredSize(new Dimension(400, 35));
        email.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Password*");
        passwordLabel.setFont(dialogFont);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        password.setPreferredSize(new Dimension(400, 35));
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dataDiNascitaLabel = new JLabel("Data di Nascita");
        dataDiNascitaLabel.setFont(dialogFont);
        dataDiNascitaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dataDiNascitaLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));
        dateComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);


        formPanel.add(nameLabel);
        formPanel.add(name);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(surnameLabel);
        formPanel.add(surname);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(emailLabel);
        formPanel.add(email);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(passwordLabel);
        formPanel.add(password);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(residenceLabel);
        formPanel.add(residence);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(professionLabel);
        formPanel.add(profession);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(phoneLabel);
        formPanel.add(phone);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(dataDiNascitaLabel);
        formPanel.add(dateComboBox);


        ButtonDecorator singInButton = new ButtonDecorator(new JButton("Signin"), 30, 30);
        singInButton.addActionListener(new SignInListener(email.getText(), new String(password.getPassword()), name.getText(), surname.getText(), residence.getText(), profession.getText(),
                phone.getText(), dateComboBox.getSelectedDate()));


        JPanel centerPanel = new JPanel();
        JPanel gridPanel = new JPanel(new GridLayout(1, 3));

        gridPanel.add(new JLabel(""));
        gridPanel.add(formPanel);
        gridPanel.add(new JLabel(""));


        // Pannello per il pulsante "SignIn"
        JPanel signInPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        signInPanel.add(singInButton);

        // Pannello principale

        add(gridPanel, BorderLayout.CENTER);
        add(signInPanel, BorderLayout.SOUTH);


    }

    public String getEmail() {
        return email.getText();
    }

    public String getName() {
        return name.getText();
    }

    public String getSurname() {
        return surname.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public String getResidence() {
        return residence.getText();
    }

    public String getProfession() {
        return profession.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public LocalDate getDate() {
        return dateComboBox.getSelectedDate();
    }

    public void setEmptyTextField() {
        name.setText("");
        surname.setText("");
        email.setText("");
        password.setText("");
        residence.setText("");
        profession.setText("");
        phone.setText("");
    }
}
