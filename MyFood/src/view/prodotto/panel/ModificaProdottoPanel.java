package view.prodotto.panel;

import business.AmministratoreBusiness;
import business.result.ProductResult;
import model.prodotto.composite.IProdotto;
import model.prodotto.composite.Menu;
import model.prodotto.composite.Prodotto_Singolo;
import view.MainFrame;
import view.catalogo.panel.CatalogoPanel;
import view.general.decorator.ButtonDecorator;
import view.general.decorator.PanelDecorator;
import view.profilo.listener.ProfiloListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificaProdottoPanel extends JMenuBar {
    private SpinnerNumberModel decimalModel = new SpinnerNumberModel(0, 0, 100, 0.01);
    private JSpinner prezzoField = new JSpinner(decimalModel);

    {
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(prezzoField, "#,##0.00");
        prezzoField.setEditor(editor);

    }

    private SpinnerNumberModel integerModel = new SpinnerNumberModel(0, 0, 100, 1);
    private JSpinner scontoField = new JSpinner(integerModel);

    {
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(scontoField, "###");
        scontoField.setEditor(editor);

    }

    private JCheckBox disponibilita = new JCheckBox();
    private JPanel disponibilitaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel prezzoLabel = new JLabel("Prezzo");
    private JLabel disponibilitaLabel = new JLabel("Disponibilità");
    private JLabel scontoLabel = new JLabel("Sconto (%)");

    public ModificaProdottoPanel(IProdotto prodotto) {
        super();
        setLayout(new BorderLayout());
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        PanelDecorator formPanel = new PanelDecorator(new JPanel(), 30, 30);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new LineBorder((Color.LIGHT_GRAY), 20));

        prezzoLabel.setFont(dialogFont);
        prezzoLabel.setAlignmentX(LEFT_ALIGNMENT);
        prezzoLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        prezzoField.setValue(prodotto.getPrezzo());
        prezzoField.setFont(dialogFont);
        prezzoField.setPreferredSize(new Dimension(400, 20));
        prezzoField.setAlignmentX(Component.LEFT_ALIGNMENT);

        scontoLabel.setFont(dialogFont);
        scontoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        scontoLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));
        if (prodotto instanceof Menu) {
            Float f = ((Menu) prodotto).getSconto() * 100;
            Integer sconto = f.intValue();
            scontoField.setValue(sconto);
        }
        scontoField.setFont(dialogFont);
        scontoField.setPreferredSize(new Dimension(400, 20));
        scontoField.setAlignmentX(Component.LEFT_ALIGNMENT);


        disponibilitaLabel.setFont(dialogFont);
        disponibilitaLabel.setBorder(new MatteBorder(0, 10, 0, 0, Color.LIGHT_GRAY));

        disponibilita.setBackground(Color.LIGHT_GRAY);
        disponibilitaPanel.setBackground(Color.LIGHT_GRAY);
        disponibilitaPanel.setAlignmentX(LEFT_ALIGNMENT);
        disponibilitaPanel.add(disponibilitaLabel);
        disponibilitaPanel.add(disponibilita);
        instanceofProduct(prodotto);

        formPanel.add(prezzoLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(prezzoField);
        formPanel.add(scontoLabel);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(scontoField);
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(new JLabel("\n\n"));
        formPanel.add(disponibilitaPanel);
        formPanel.add(new JLabel("\n\n"));


        ButtonDecorator confermaButton = new ButtonDecorator(new JButton("Conferma"), 30, 30);
        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(formPanel);
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));

        add(gridPanel, BorderLayout.CENTER);
        // Pannello per il pulsante "Conferma"
        ActionListener confermaListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = MainFrame.getInstance();
                mainFrame.remove(mainFrame.getContentPane().getComponent(1));
                if (prodotto instanceof Prodotto_Singolo) {
                    Float p;
                    ((Prodotto_Singolo) prodotto).setDisponibilita(disponibilita.isSelected());
                    if (prezzoField.getValue() instanceof Float) {
                        p = (Float) prezzoField.getValue();
                    } else {
                        p = ((Double) prezzoField.getValue()).floatValue();
                    }
                    ((Prodotto_Singolo) prodotto).setPrezzo(p);
                    ProductResult productResult = AmministratoreBusiness.getInstance().updateProduct(prodotto);
                    JOptionPane.showMessageDialog(null, productResult.getMessage(), "Risultato", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.add(CatalogoPanel.getInstance(), 1);

                } else if (prodotto instanceof Menu) {
                    Float s = (((Integer) scontoField.getValue()).floatValue() / 100);
                    ((Menu) prodotto).setSconto((s));
                    ProductResult productResult = AmministratoreBusiness.getInstance().updateProduct(prodotto);
                    JOptionPane.showMessageDialog(null, productResult.getMessage(), "Risultato", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.add(CatalogoPanel.getInstance(), 1);
                }
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        };
        confermaButton.addActionListener(confermaListener);

        JPanel confermaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confermaPanel.add(confermaButton);

        add(confermaPanel, BorderLayout.SOUTH);
    }

    private void instanceofProduct(IProdotto prodotto) {
        if (prodotto instanceof Prodotto_Singolo) {
            disponibilitaPanel.setVisible(true);
            prezzoLabel.setVisible(true);
            prezzoField.setVisible(true);
            disponibilitaLabel.setVisible(true);
            if (((Prodotto_Singolo) prodotto).getDisponibilita()) {
                disponibilita.setSelected(true);
            } else disponibilita.setSelected(false);
            disponibilita.setVisible(true);
            scontoLabel.setVisible(false);
            scontoField.setVisible(false);
        } else {
            scontoField.setValue(((Menu) prodotto).getSconto());
            disponibilitaPanel.setVisible(false);
            prezzoLabel.setVisible(false);
            prezzoField.setVisible(false);
            disponibilitaLabel.setVisible(false);
            disponibilita.setVisible(false);
            scontoLabel.setVisible(true);
            scontoField.setVisible(true);
        }
    }

}
