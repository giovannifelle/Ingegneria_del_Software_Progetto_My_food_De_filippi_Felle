package view;

import javax.swing.*;
import java.awt.*;



public class MainLoadingFrame extends JFrame {
    public static final ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\image\\logo\\logo.jpg");
    public MainLoadingFrame() {
        setUndecorated(true);
        setTitle("Loading...");
        setIconImage(icon.getImage());
        setSize(350, 300);
        setLocationRelativeTo(null); // Centro dello schermo
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Non permettere la chiusura durante il caricamento

        Image scaledImage = icon.getImage().getScaledInstance(getWidth(), 295, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);

        // Crea una JLabel che contiene l'immagine ridimensionata
        JLabel logoLabel = new JLabel(scaledLogoIcon, SwingConstants.CENTER);

        // Aggiungi il logo al frame
        add(logoLabel, BorderLayout.CENTER);
    }
}