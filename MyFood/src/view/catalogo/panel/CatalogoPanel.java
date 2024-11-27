package view.catalogo.panel;

import business.CatalogoBusiness;
import model.prodotto.composite.IProdotto;
import view.MainFrame;
import view.access.panel.LogInPanel;
import view.catalogo.listener.CatalogoListener;
import view.catalogo.listener.ChangeCatalogoViewListener;
import view.general.decorator.ButtonDecorator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CatalogoPanel extends JPanel {
    private static CatalogoPanel instance; // Modificato da final a non final
    private static JComboBox<String> tipologiaProdotto;
    private static JComboBox<String> ingredienti;


    private static ButtonDecorator aggiorna = new ButtonDecorator(new JButton("Aggiorna"), 30, 30);
    private static final JLabel filtra = new JLabel("Filtra per:   ");

    public static CatalogoPanel getInstance() {
        if (instance == null) {
            instance = new CatalogoPanel(); // Crea una nuova istanza se necessario
        }
        return instance;
    }


    private CatalogoPanel() {
        super();
        setLayout(new BorderLayout());
        add(fillFilterPanel(), BorderLayout.NORTH);
        add(fillCatalogoPanel(CatalogoBusiness.getProducts()), BorderLayout.CENTER);
    }

    public static void fillComboBox() {
        try {
            tipologiaProdotto = new JComboBox<>(CatalogoBusiness.getProductsFilterName().toArray(new String[0]));
            ingredienti = new JComboBox<>(CatalogoBusiness.getIngredientsFilterName().toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e); // Lancia l'errore per bloccare ulteriori problemi
        }
    }

    public static JScrollPane fillCatalogoPanel(ArrayList<IProdotto> listaDiProdotti) {
        JPanel catalogoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        catalogoPanel.setPreferredSize(new Dimension(MainFrame.SCREEN_DIMENSION.width - 11, MainFrame.SCREEN_DIMENSION.height));
        for (IProdotto p : listaDiProdotti) {
            ButtonDecorator buttonDecorator = new ButtonDecorator(new JButton("<html><div style='text-align: center; '>" + p.getNome() + "</div></html>"), 30, 30);
            buttonDecorator.setPreferredSize(new Dimension(250, 100));
            buttonDecorator.addActionListener(new ChangeCatalogoViewListener());
            catalogoPanel.add(buttonDecorator);
        }
        JScrollPane scrollPane = new JScrollPane(catalogoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    public static String getFiltroProdotto() {
        return tipologiaProdotto.getSelectedItem().toString();
    }

    public static String getFiltroIngrediente() {
        return ingredienti.getSelectedItem().toString();
    }

    public void setEmptyIngredienti() {
        ingredienti.setSelectedItem("Tutti");
        ingredienti.setEnabled(false);
    }

    public void setEmptyComboBox() {
        ingredienti.setSelectedItem("Tutti");
        tipologiaProdotto.setSelectedItem("Tutti");
    }

    public void setEnableIngredienti() {
        ingredienti.setEnabled(true);
    }

    public static void repaintCatalogo() {
        CatalogoPanel.getInstance().remove(0);
        CatalogoPanel.getInstance().remove(0);
        CatalogoPanel.getInstance().setEmptyComboBox();
        CatalogoPanel.getInstance().add(fillFilterPanel(),BorderLayout.NORTH);
        CatalogoPanel.getInstance().add(fillCatalogoPanel(CatalogoBusiness.getProducts()), BorderLayout.CENTER);
    }

    public static JPanel fillFilterPanel(){
        Font dialogFont = new Font("Dialog", Font.BOLD, 20);
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //riempio i filtri
        fillComboBox();

        filtra.setFont(dialogFont);
        tipologiaProdotto.setFont(dialogFont);
        tipologiaProdotto.addActionListener(new CatalogoListener());
        ingredienti.setFont(dialogFont);
        aggiorna.addActionListener(new CatalogoListener());
        comboBoxPanel.add(filtra);
        comboBoxPanel.add(tipologiaProdotto);
        comboBoxPanel.add(ingredienti);
        comboBoxPanel.add(new JLabel("    "));
        comboBoxPanel.add(aggiorna);
        return comboBoxPanel;
    }
}
