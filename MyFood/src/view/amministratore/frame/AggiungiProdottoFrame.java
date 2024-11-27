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
import java.nio.file.*;

public class AggiungiProdottoFrame extends JFrame {


    private TextFieldDecorator nomeField = new TextFieldDecorator(new JTextField(), 30, 30);
    private SpinnerNumberModel decimalModel = new SpinnerNumberModel(0, 0, 100, 0.01);
    private  JSpinner prezzoField = new JSpinner(decimalModel);

     {
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(prezzoField, "#,##0.00");
        prezzoField.setEditor(editor);

    }

    private JCheckBox disponibilita = new JCheckBox();
    private JComboBox<String> tipologiaProdotto = new JComboBox<>();
    private JComboBox<String> ingredientiComboBox = new JComboBox<>();
    private ButtonDecorator addImmagini = new ButtonDecorator(new JButton("+"), 30, 30);
    private JLabel immagini = new JLabel();
    private ButtonDecorator addIngrediente = new ButtonDecorator(new JButton("+"), 30, 30);
    private ButtonDecorator confermaButton = new ButtonDecorator(new JButton("Conferma"), 30, 30);
    private JLabel ingredienti = new JLabel();


    public AggiungiProdottoFrame() {
        super("Aggiungi Prodotto");
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

        //tipologia prodotto
        JLabel tipologiaLabel = new JLabel("Tipologia");
        tipologiaLabel.setFont(dialogFont);
        tipologiaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottoPanel.add(tipologiaLabel);
        tipologiaProdotto.setAlignmentX(Component.LEFT_ALIGNMENT);
        tipologiaProdotto.setFont(dialogFont);
        tipologiaProdotto.setPreferredSize(new Dimension(400, 35));
        tipologiaProdotto.setMaximumSize(new Dimension(400, 35));
        prodottoPanel.add(tipologiaProdotto);
        prodottoPanel.add(Box.createVerticalStrut(10));

        //prezzo
        JLabel prezzoLabel = new JLabel("Prezzo");
        prezzoLabel.setFont(dialogFont);
        prezzoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottoPanel.add(prezzoLabel);
        prezzoField.setAlignmentX(Component.LEFT_ALIGNMENT);
        prezzoField.setFont(dialogFont);
        prezzoField.setPreferredSize(new Dimension(400, 35));
        prezzoField.setMaximumSize(new Dimension(400, 35));
        prodottoPanel.add(prezzoField);
        prodottoPanel.add(Box.createVerticalStrut(10));

        //disponibilità
        JPanel disponibilitaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        disponibilitaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel disponibilitaLabel = new JLabel("Disponibilità");
        disponibilitaLabel.setFont(dialogFont);
        disponibilitaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        disponibilitaPanel.add(disponibilitaLabel);
        disponibilita.setAlignmentX(Component.LEFT_ALIGNMENT);
        disponibilitaPanel.add(disponibilita);
        prodottoPanel.add(disponibilitaPanel);
        prodottoPanel.add(Box.createVerticalStrut(10));

        //ingredienti
        JLabel ingredientiLabel = new JLabel("Ingredienti");
        ingredientiLabel.setFont(dialogFont);
        ingredientiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prodottoPanel.add(ingredientiLabel);
        JPanel ingredientiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addIngrediente.setAlignmentX(Component.LEFT_ALIGNMENT);
        addIngrediente.addActionListener(new ActionListener() {
            String ing = ingredienti.getText();

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, ingredientiComboBox, "Seleziona un ingrediente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                String selectedOption = "";
                if (result == JOptionPane.OK_OPTION) {
                    selectedOption = (String) ingredientiComboBox.getSelectedItem();
                    ing += selectedOption + ", ";
                    ingredienti.setText(ing);
                    revalidate();
                    repaint();
                }

            }
        });
        ingredientiPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientiPanel.add(addIngrediente);
        ingredienti.setFont(dialogFont);
        ingredientiPanel.add(ingredienti);
        prodottoPanel.add(ingredientiPanel);
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
                ProductResult productResult = ProdottoBusiness.addProdotto(nomeField.getText(), (String) tipologiaProdotto.getSelectedItem(), ingredienti.getText(), prezzoField.getValue(), immagini.getText(), disponibilita.isSelected());
                switch (productResult.getProductResult()) {
                    case ADDED_PRODUCT:
                        JOptionPane.showMessageDialog(null, productResult.getMessage(), "Successo", JOptionPane.INFORMATION_MESSAGE);

                        CatalogoPanel.repaintCatalogo();
                        dispose();
                        break;
                    case EXIST:
                        JOptionPane.showMessageDialog(null, productResult.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            ingredientiComboBox = new JComboBox<>(CatalogoBusiness.getIngredientsFilterName().toArray(new String[0]));
            tipologiaProdotto = new JComboBox<>(CatalogoBusiness.getProductsFilterName().toArray(new String[0]));
            ingredientiComboBox.removeItemAt(0);
            tipologiaProdotto.removeItemAt(0);
            tipologiaProdotto.removeItemAt(tipologiaProdotto.getItemCount() - 1);
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