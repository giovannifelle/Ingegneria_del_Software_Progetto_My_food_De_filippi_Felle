package view.carrello.utils;

import view.MainFrame;

import javax.swing.*;

public class LoadingFrame extends JFrame {

    public LoadingFrame() {
        setIconImage(MainFrame.icon.getImage());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Non permettere la chiusura durante il caricamento
        setSize(300, 100);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Elaborazione in corso...");
        setLocationRelativeTo(null); // Centro dello schermo
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel.add(label);
        panel.add(progressBar);
        add(panel);
    }
}
