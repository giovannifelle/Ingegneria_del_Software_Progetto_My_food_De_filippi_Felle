package business;

import dao.ingrediente.IngredienteDAO;
import dao.prodotto.ProdottoDAO;
import dao.prodotto.Tipologia_ProdottoDAO;
import model.Ingrediente;
import model.Tipologia_Prodotto;
import model.prodotto.composite.IProdotto;
import model.prodotto.composite.Menu;
import model.prodotto.composite.Prodotto_Singolo;

import java.util.ArrayList;

public class CatalogoBusiness {
    private static CatalogoBusiness instance;

    private static SessionBusiness session = SessionBusiness.getInstance();
    public static final String CATALOGO = "CATALOGO";
    public static final String FILTRO_INGREDIENTI = "FILTRO_INGREDIENTI";
    public static final String FILTRO_PRODOTTI = "FILTRO_PRODOTTI";

    public static ArrayList<Ingrediente> getIngredientsFilter() {
        return (ArrayList<Ingrediente>) session.getFromSession(FILTRO_INGREDIENTI);
    }

    public static ArrayList<String> getIngredientsFilterName() {
        ArrayList<String> ingredienti = new ArrayList<>();
        ingredienti.add("Tutti");
        for (Ingrediente i : getIngredientsFilter()) {
            ingredienti.add(i.getNome());
        }
        return ingredienti;
    }
    public static ArrayList<String> getProductsName() {
        ArrayList<String> prodotti = new ArrayList<>();
        for (IProdotto i : getProducts()) {
            prodotti.add(i.getNome());
        }
        return prodotti;
    }

    public static ArrayList<String> getProductsFilterName() {
        ArrayList<String> prodotti = new ArrayList<>();
        prodotti.add("Tutti");
        for (Tipologia_Prodotto tP : getProductsFilter()) {
            prodotti.add(tP.getNome());
        }
        prodotti.add("Menu");
        return prodotti;
    }

    public static ArrayList<Tipologia_Prodotto> getProductsFilter() {
        return (ArrayList<Tipologia_Prodotto>) session.getFromSession(FILTRO_PRODOTTI);
    }


    public static ArrayList<IProdotto> getProducts() {
        return (ArrayList<IProdotto>) session.getFromSession(CATALOGO);
    }

    private CatalogoBusiness() {
    }

    public static CatalogoBusiness getInstance() {
        if (instance == null) {
            instance = new CatalogoBusiness();
        }
        return instance;
    }

    public static void loadFilter() {
        ArrayList<Tipologia_Prodotto> filtriProdotti = new ArrayList<>();
        ArrayList<Ingrediente> filtriIngredienti = new ArrayList<>();

        Tipologia_ProdottoDAO tipologiaProdottoDAO = Tipologia_ProdottoDAO.getInstance();
        IngredienteDAO ingredienteDAO = IngredienteDAO.getInstance();

        filtriProdotti = tipologiaProdottoDAO.findAll();
        filtriIngredienti = ingredienteDAO.findAll();

        session.putInSession(FILTRO_PRODOTTI, filtriProdotti);
        session.putInSession(FILTRO_INGREDIENTI, filtriIngredienti);

    }

    public static void loadProducts() {
        ArrayList<IProdotto> prodotti = new ArrayList<>();

        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();

        prodotti = prodottoDAO.findAll();

        session.putInSession(CATALOGO, prodotti);
    }

    public static ArrayList<IProdotto> loadFilteredProducts(String tipoProdotto, String ingrediente) {
        ArrayList<IProdotto> prodottiFiltrati = new ArrayList<>();
        ArrayList<IProdotto> catalogo = (ArrayList<IProdotto>) session.getFromSession(CATALOGO);
        if (tipoProdotto.equals("Menu")) {
            for (IProdotto p : catalogo) {
                if (p instanceof Menu) {
                    prodottiFiltrati.add(p);
                }
            }
            return prodottiFiltrati;
        }
        if (!tipoProdotto.equals("Tutti") && !ingrediente.equals("Tutti")) {
            for (IProdotto p : catalogo) {
                if (p instanceof Prodotto_Singolo) {
                    for (Ingrediente i : ((Prodotto_Singolo) p).getIngredienti()) {
                        if (i.getNome().equals(ingrediente) && ((Prodotto_Singolo) p).getTipologiaP().getNome().equals(tipoProdotto)) {
                            prodottiFiltrati.add(p);
                        }
                    }
                }
            }
            return prodottiFiltrati;
        } else if (!tipoProdotto.equals("Tutti")) {
            for (IProdotto p : catalogo) {
                if (p instanceof Prodotto_Singolo) {
                    if (((Prodotto_Singolo) p).getTipologiaP().getNome().equals(tipoProdotto)) {
                        prodottiFiltrati.add(p);
                    }
                }
            }
            return prodottiFiltrati;
        } else if (!ingrediente.equals("Tutti")) {
            for (IProdotto p : catalogo) {
                if (p instanceof Prodotto_Singolo) {
                    for (Ingrediente i : ((Prodotto_Singolo) p).getIngredienti()) {
                        if (i.getNome().equals(ingrediente)) {
                            prodottiFiltrati.add(p);
                        }
                    }
                }
            }
            return prodottiFiltrati;
        }
        return catalogo;
    }


}

