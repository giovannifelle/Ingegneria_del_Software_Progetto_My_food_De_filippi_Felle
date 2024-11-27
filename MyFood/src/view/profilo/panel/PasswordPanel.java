package view.profilo.panel;

import view.access.listener.SignInListener;
import view.access.panel.SignInPanel;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;
import view.general.decorator.PasswordFieldDecorator;
import view.general.decorator.TextFieldDecorator;
import view.profilo.listener.ProfiloListener;
import view.utils.DateComboBox;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class PasswordPanel extends JMenuBar {
    private static PasswordPanel instance = new PasswordPanel();


    private PasswordFieldDecorator oldPassword = new PasswordFieldDecorator(new JPasswordField(), 30, 30);

    private PasswordFieldDecorator newPassword = new PasswordFieldDecorator(new JPasswordField(), 30, 30);

    public static PasswordPanel getInstance() {
        if (instance == null) {
            instance = new PasswordPanel(); // Crea una nuova istanza se necessario
        }
        return instance;
    }

    private PasswordPanel() {
        super();
        setLayout(new BorderLayout());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        PanelDecorator formPanel = new PanelDecorator(new JPanel(), 30, 30);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new LineBorder((Color.LIGHT_GRAY), 20));

        JLabel oldPasswordLabel = new JLabel("Vecchia Password");
        oldPasswordLabel.setFont(dialogFont);
        oldPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        oldPasswordLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        oldPassword.setPreferredSize(new Dimension(400, 35));
        oldPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel newPasswordLabel = new JLabel("Nuova Password");
        newPasswordLabel.setFont(dialogFont);
        newPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPasswordLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        newPassword.setPreferredSize(new Dimension(400, 35));
        newPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(oldPasswordLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(oldPassword);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(newPasswordLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(newPassword);

        ButtonDecorator confermaButton = new ButtonDecorator(new JButton("Conferma"), 30, 30);
        confermaButton.addActionListener(new ProfiloListener());
        JPanel gridPanel=new JPanel(new GridLayout(3,3));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(formPanel);
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));

        add(gridPanel,BorderLayout.CENTER);
        // Pannello per il pulsante "Conferma"
        JPanel confermaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confermaPanel.add(confermaButton);
        add(confermaPanel, BorderLayout.SOUTH);
    }

    public String getOldPassword(){
        return new String(oldPassword.getPassword());
    }

    public String getNewPassword(){
        return new String(newPassword.getPassword());
    }

    public void setEmptyTextField() {
        oldPassword.setText("");
        newPassword.setText("");
    }
}
