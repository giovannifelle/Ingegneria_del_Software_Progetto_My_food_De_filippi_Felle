package view.access.listener;

import business.LoginBusiness;
import business.result.LoginResult;
import view.*;
import view.access.frame.SignInFrame;
import view.access.panel.LogInPanel;
import view.general.panel.NavigationPanel;
import view.access.panel.SignInPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInListener implements ActionListener {

    private String email, password, tipo;


    public LogInListener() {
    }

    public LogInListener(String email, String password, String tipo) {
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LogInPanel logInPanel = LogInPanel.getInstance();
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();
        if (!buttonText.equals(logInPanel.getUser())) {
            MainFrame mainFrame = MainFrame.getInstance();
            switch (buttonText) {
                case "Cliente":
                    logInPanel.colorSelectedButton("Cliente");
                    logInPanel.setUser("Cliente");
                    break;
                case "Amministratore":
                    logInPanel.colorSelectedButton("Amministratore");
                    logInPanel.setUser("Amministratore");
                    break;
                case "Cucina":
                    logInPanel.colorSelectedButton("Cucina");
                    logInPanel.setUser("Cucina");
                    break;
                case "Login":
                    NavigationPanel navigationPanel = NavigationPanel.getInstance();
                    LoginBusiness loginBusiness = LoginBusiness.getInstance();
                    email = logInPanel.getEmail();
                    password = logInPanel.getPassword();
                    tipo = logInPanel.getTipo();
                    LoginResult loginResult = loginBusiness.login(email, password, tipo);
                    switch (loginResult.getLoginResult()) {
                        case LOGIN_OK:
                            JOptionPane.showMessageDialog(null, loginResult.getMessage(), "Accesso Riuscito", JOptionPane.INFORMATION_MESSAGE);
                            navigationPanel.switchAtCurrentUserView(tipo);
                            break;
                        case USER_DOESNT_EXIST:
                            JOptionPane.showMessageDialog(null, loginResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            break;
                        case USER_BLOCKED:
                            JOptionPane.showMessageDialog(null, loginResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            break;
                        case WRONG_PASSWORD:
                            JOptionPane.showMessageDialog(null, loginResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            break;
                    }


                    break;
                case "Registrati":
                    SignInPanel.getInstance().setEmptyTextField();
                    SignInFrame frame = SignInFrame.getInstance();
                    frame.setVisible(true);
            }
            mainFrame.revalidate();
            mainFrame.repaint();
        }

    }
}
