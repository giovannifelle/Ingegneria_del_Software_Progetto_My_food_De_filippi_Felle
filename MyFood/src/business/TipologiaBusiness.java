package business;

import dao.ingrediente.Tipologia_IngredienteDAO;
import dao.prodotto.Tipologia_ProdottoDAO;
import model.Tipologia_Ingrediente;
import model.Tipologia_Prodotto;


import java.util.ArrayList;

public class TipologiaBusiness {

    public static final String TIPOLOGIA_INGREDIENTI = "TIPOLOGIA_INGREDIENTI";

    private static TipologiaBusiness instance;

    private static SessionBusiness session = SessionBusiness.getInstance();

    private static Tipologia_IngredienteDAO tipologiaIngredienteDAO = Tipologia_IngredienteDAO.getInstance();
    private static Tipologia_ProdottoDAO tipologiaProdottoDAO = Tipologia_ProdottoDAO.getInstance();

    public static TipologiaBusiness getInstance() {
        if (instance == null) {
            instance = new TipologiaBusiness();
        }
        return instance;
    }

    public static void loadTipologiaIngredienti() {
        ArrayList<Tipologia_Ingrediente> tp_i = new ArrayList<>();

        tp_i = tipologiaIngredienteDAO.findAll();

        session.putInSession(TIPOLOGIA_INGREDIENTI, tp_i);

    }

    public static ArrayList<String> getTipologieIngredientiNames() {
        ArrayList<String> tp_ingredienti = new ArrayList<>();
        for (Tipologia_Ingrediente tp : getTipologieIngredienti()) {
            tp_ingredienti.add(tp.getNome());
        }
        return tp_ingredienti;
    }

    public static ArrayList<Tipologia_Ingrediente> getTipologieIngredienti() {
        return (ArrayList<Tipologia_Ingrediente>) session.getFromSession(TIPOLOGIA_INGREDIENTI);
    }

    public static void addTipologiaProdotto(String nome) {
        Tipologia_Prodotto tp = new Tipologia_Prodotto();
        tp.setNome(nome);
        tipologiaProdottoDAO.addTipologiaProdotto(tp);
        session.removeFromSession(CatalogoBusiness.FILTRO_PRODOTTI);
        session.removeFromSession(CatalogoBusiness.FILTRO_INGREDIENTI);
        CatalogoBusiness.loadFilter();
    }

    public static void addTipologiaIngrediente(String nome) {
        Tipologia_Ingrediente ti = new Tipologia_Ingrediente();
        ti.setNome(nome);
        tipologiaIngredienteDAO.addTipologiaIngrediente(ti);
        session.removeFromSession(TIPOLOGIA_INGREDIENTI);
        loadTipologiaIngredienti();
    }
}
