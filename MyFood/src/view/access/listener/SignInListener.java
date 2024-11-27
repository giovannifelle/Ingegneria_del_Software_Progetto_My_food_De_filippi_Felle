package view.access.listener;

import business.ClienteBusiness;
import business.result.SigninResult;
import view.access.frame.SignInFrame;
import view.general.panel.NavigationPanel;
import view.access.panel.SignInPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class SignInListener implements ActionListener {

    private String email, password, nome, cognome, residenza, professione, numTelefono;
    private LocalDate date;


    public SignInListener(String email, String password, String nome, String cognome, String residenza, String professione, String numTelefono, LocalDate date) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.residenza = residenza;
        this.numTelefono = numTelefono;
        this.professione = professione;
        this.date = date;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SignInPanel signInPanel = SignInPanel.getInstance();
        SignInFrame frame = SignInFrame.getInstance();
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();
        if (buttonText.equals("Signin")) {
            NavigationPanel navigationPanel = NavigationPanel.getInstance();
            ClienteBusiness clienteBusiness = ClienteBusiness.getInstance();
            email = signInPanel.getEmail();
            password = signInPanel.getPassword();
            nome = signInPanel.getName();
            cognome = signInPanel.getSurname();
            residenza = signInPanel.getResidence();
            if(signInPanel.getPhone().length()>9){
            numTelefono = signInPanel.getPhone().substring(0,9);}
            else{
                numTelefono=signInPanel.getPhone();
            }
            professione = signInPanel.getProfession();
            date = signInPanel.getDate();
            SigninResult signinResult =clienteBusiness.signin(email, password, nome, cognome, residenza, numTelefono,professione, date);
            switch (signinResult.getSigninResult()) {
                case USER_EXIST:
                    JOptionPane.showMessageDialog(null, signinResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                    break;
                case EMPTY_BOX:
                    JOptionPane.showMessageDialog(null, signinResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    break;
                case SIGNIN_OK:
                    JOptionPane.showMessageDialog(null, signinResult.getMessage(), "Accesso Riuscito", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                  /*  LoginResult loginResult = LoginBusiness.getInstance().login(email, password, "Cliente");
                    JOptionPane.showMessageDialog(null, loginResult.getMessage(), "Accesso Riuscito", JOptionPane.INFORMATION_MESSAGE);
                    navigationPanel.switchAtCurrentUserView("Cliente");*/
                    break;

            }
        }
    }
}

