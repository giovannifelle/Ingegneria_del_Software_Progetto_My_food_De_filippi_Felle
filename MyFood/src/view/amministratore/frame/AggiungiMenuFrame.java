package view.amministratore.frame;

import business.CatalogoBusiness;
import business.ProdottoBusiness;
import business.result.ProductResult;
import view.MainFrame;
import view.catalogo.panel.CatalogoPanel;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.TextFieldDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AggiungiMenuFrame extends JFrame {


    private TextFieldDecorator nomeField = new TextFieldDecorator(new JTextField(), 30, 30);

    private String listaProdotti=new String();
    private  SpinnerNumberModel integerModel = new SpinnerNumberModel(0, 0, 100, 1);
    private  JSpinner prezzoField = new JSpinner(integerModel);

    {
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(prezzoField, "###");
        prezzoField.setEditor(editor);

    }

    private JComboBox<String> prodottiComboBox = new JComboBox<>();

    private ButtonDecorator addImmagini = new ButtonDecorator(new JButton("+"), 30, 30);

    private JLabel immagini = new JLabel();

    private  ButtonDecorator addProdotto = new ButtonDecorator(new JButton("+"), 30, 30);
    private ButtonDecorator confermaButton = new ButtonDecorator(new JButton("Conferma"), 30, 30);

    private JLabel prodotti = new JLabel("");


    public AggiungiMenuFrame() {
        super("Aggiungi Menu");
        setIconImage(MainFrame.icon.getImage());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        setSize(new Dimension(MainFrame.SCREEN_DIMENSION.width, MainFrame.SCREEN_DIMENSION.height - 40));
        setLocation(0, 0);
        setLayout(new BorderLayout());
        add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        JPanel prodottoPanel = new JPanel();
        prodottoPanel.setLayout(new BoxLayout(prodottoPanel, BoxLayout.Y_AXIS));



        //nome
        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setFont(dialogFont);
        nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottoPanel.add(nomeLabel);
        nomeField.setPreferredSize(new Dimension(400, 35));
        nomeField.setMaximumSize(new Dimension(400, 35));
        nomeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottoPanel.add(nomeField);
        prodottoPanel.add(Box.createVerticalStrut(10));

        //carico i dati nelle combobox
        fillComboBox();

        //sconto
        JLabel prezzoLabel = new JLabel("Sconto (%)");
        prezzoLabel.setFont(dialogFont);
        prezzoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottoPanel.add(prezzoLabel);
        prezzoField.setAlignmentX(Component.LEFT_ALIGNMENT);
        prezzoField.setFont(dialogFont);
        prezzoField.setPreferredSize(new Dimension(400, 35));
        prezzoField.setMaximumSize(new Dimension(400, 35));
        prodottoPanel.add(prezzoField);
        prodottoPanel.add(Box.createVerticalStrut(10));

        //prodotti
        JLabel prodottiLabel = new JLabel("Prodotti");
        prodottiLabel.setFont(dialogFont);
        prodottiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottoPanel.add(prodottiLabel);
        JPanel prodottiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addProdotto.setAlignmentX(Component.LEFT_ALIGNMENT);
        addProdotto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, prodottiComboBox, "Seleziona un prodotto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                String selectedOption = "";
                if (result == JOptionPane.OK_OPTION) {
                    selectedOption = (String) prodottiComboBox.getSelectedItem();
                    listaProdotti += selectedOption + ", ";
                    prodotti.setText(listaProdotti);
                    revalidate();
                    repaint();
                }

            }
        });
        prodottiPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottiPanel.add(addProdotto);
        prodotti.setFont(dialogFont);
        prodottiPanel.add(prodotti);
        prodottoPanel.add(prodottiPanel);
        prodottoPanel.add(Box.createVerticalStrut(10));

        //immagini
        JLabel immaginiLabel = new JLabel("Immagini");
        immaginiLabel.setFont(dialogFont);
        immaginiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottoPanel.add(immaginiLabel);
        JPanel immaginiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addImmagini.setAlignmentX(Component.LEFT_ALIGNMENT);
        addImmagini.addActionListener(new ActionListener() {
            String imm = "";

            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton) e.getSource()).getText().equals("+")) {
                    String s = openFileChooser();
                    if (!s.isEmpty()) {
                        imm += s + ", ";
                        immagini.setText(imm);
                        revalidate();
                        repaint();
                    }
                }
            }
        });

        immaginiPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        immaginiPanel.add(addImmagini);
        immagini.setFont(dialogFont);
        immaginiPanel.add(immagini);
        prodottoPanel.add(immaginiPanel);
        prodottoPanel.add(Box.createVerticalStrut(10));


        //conferma
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductResult productResult = ProdottoBusiness.addMenu(nomeField.getText(), prodotti.getText(), prezzoField.getValue(), immagini.getText());
                switch (productResult.getProductResult()) {
                    case ADDED_PRODUCT:
                        JOptionPane.showMessageDialog(null, productResult.getMessage(), "Successo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        CatalogoPanel.repaintCatalogo();
                        break;
                    case EXIST:
                        JOptionPane.showMessageDialog(null, productResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case ERROR:
                        JOptionPane.showMessageDialog(null, productResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        prodotti.setText("");
                        listaProdotti="";
                        revalidate();
                        repaint();
                        break;
                }
            }
        });



        add(prodottoPanel, BorderLayout.CENTER);

        JPanel confermaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confermaPanel.add(confermaButton);
        add(confermaPanel, BorderLayout.SOUTH);
    }

    public void fillComboBox() {
        try {
            prodottiComboBox = new JComboBox<>(CatalogoBusiness.getProductsName().toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e); // Lancia l'errore per bloccare ulteriori problemi
        }
    }

    public String openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home"), "Desktop"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName();
                    return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png") || filename.endsWith(".gif");
                }
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        // Mostra il file chooser e ottieni il risultato
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // Ottieni il file selezionato
            File selectedFile = fileChooser.getSelectedFile();
            File copia = new File(System.getProperty("user.dir") + "\\src\\image\\" + selectedFile.getName());

            Path source = selectedFile.toPath();
            Path destination = copia.toPath();

            try {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return selectedFile.getName();
        }

        return "";

    }
}