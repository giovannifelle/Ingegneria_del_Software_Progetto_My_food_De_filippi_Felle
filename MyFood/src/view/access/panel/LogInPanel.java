package view.access.panel;

import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;
import view.general.decorator.PasswordFieldDecorator;
import view.general.decorator.TextFieldDecorator;
import view.access.listener.LogInListener;

import javax.swing.*;
import java.awt.Component;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class LogInPanel extends JMenuBar {

    private static LogInPanel instance = new LogInPanel();

    private TextFieldDecorator email = new TextFieldDecorator(new JTextField(), 30, 30);
    private PasswordFieldDecorator password = new PasswordFieldDecorator(new JPasswordField(), 30, 30);
    private String currentUser = "Cliente";
    private static ButtonDecorator clienteButton, amministratoreButton, cucinaButton, registratiButton;
    private JPanel logInPanel = new JPanel();
    private JPanel signInPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    public static LogInPanel getInstance() {
        if (instance == null) {
            instance = new LogInPanel(); // Crea una nuova istanza se necessario
        }
        return instance;
    }

    private LogInPanel() {
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        logInPanel = loginNavigationPanel();
        buttonPanel.add(logInPanel);
        JLabel registrati = new JLabel("Nuovo cliente?      ");
        registrati.setFont(new Font("Dialog", Font.PLAIN, 15));
        signInPanel.add(registrati);
        registratiButton = new ButtonDecorator(new JButton("Registrati"), 30, 30);
        registratiButton.addActionListener(new LogInListener());
        signInPanel.add(registratiButton);
        buttonPanel.add(signInPanel);
        add(buttonPanel, BorderLayout.NORTH);

        //Formapanel
        PanelDecorator formPanel = new PanelDecorator(new JPanel(), 30, 30);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new LineBorder((Color.LIGHT_GRAY), 20));

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(dialogFont);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(dialogFont);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        email.setPreferredSize(new Dimension(400, 35));

        email.setAlignmentX(Component.LEFT_ALIGNMENT);

        password.setPreferredSize(new Dimension(400, 35));
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(emailLabel);
        formPanel.add(email);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(passwordLabel);
        formPanel.add(password);


        ButtonDecorator accediButton = new ButtonDecorator(new JButton("Login"), 30, 30);
        accediButton.addActionListener(new LogInListener());


        JPanel accediPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel centerPanel = new JPanel();
        JPanel gridPanel = new JPanel(new GridLayout(3, 3));

        gridPanel.add(new JLabel(""));
        gridPanel.add(formPanel);

        centerPanel.add(gridPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        accediPanel.add(accediButton);

        add(accediPanel, BorderLayout.SOUTH);



        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "invio");
        getActionMap().put("invio", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accediButton.doClick();
            }
        });

    }


    public JPanel loginNavigationPanel() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        clienteButton = new ButtonDecorator(new JButton("Cliente"), 30, 30);
        amministratoreButton = new ButtonDecorator(new JButton("Amministratore"), 30, 30);
        cucinaButton = new ButtonDecorator(new JButton("Cucina"), 30, 30);

        LogInListener loginListener = new LogInListener();

        clienteButton.addActionListener(loginListener);
        amministratoreButton.addActionListener(loginListener);
        cucinaButton.addActionListener(loginListener);

        panel.add(clienteButton);
        clienteButton.setColor(ButtonDecorator.colorOrange);
        panel.add(amministratoreButton);
        panel.add(cucinaButton);
        return panel;

    }

    public void setUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getUser() {
        return currentUser;
    }

    public void colorSelectedButton(String text) {
        ArrayList<Component> allComponents = new ArrayList<Component>();
        allComponents.addAll(Arrays.asList(logInPanel.getComponents()));
        allComponents.addAll(Arrays.asList(signInPanel.getComponents()));
        for (Component button : allComponents) {
            if (button instanceof ButtonDecorator) {
                if (((ButtonDecorator) button).getText().equals(text)) {
                    ((ButtonDecorator) button).setColor(ButtonDecorator.colorOrange);
                } else {
                    ((ButtonDecorator) button).setColor(Color.LIGHT_GRAY);
                }
            }
        }
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public String getTipo() {
        return currentUser;
    }
    public void setEmptyTextField(){
        email.setText("");
        password.setText("");
    }
}
