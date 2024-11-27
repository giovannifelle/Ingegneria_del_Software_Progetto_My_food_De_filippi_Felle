package business;

import dao.ingrediente.IngredienteDAO;
import model.Ingrediente;
import model.Tipologia_Ingrediente;
import model.ente.Produttore;
import model.ente.Distributore;
import java.util.ArrayList;

public class IngredienteBusiness {

    public static IngredienteBusiness instance;
    private static IngredienteDAO ingredienteDAO= IngredienteDAO.getInstance();
    private static SessionBusiness session =SessionBusiness.getInstance();

    private IngredienteBusiness() {
    }

    public static IngredienteBusiness getInstance() {
        if (instance == null) {
            instance = new IngredienteBusiness();
        }
        return instance;
    }

    public static void addIngrediente(String nome, String prod, String distr, String tipologia) {
        Ingrediente i=new Ingrediente();
        i.setNome(nome);
        for(Produttore p : EnteBusiness.getProduttori()){
            if(p.getNome().equals(prod)){
                i.setProduttore(p);
            }
        }
        for(Tipologia_Ingrediente tp: TipologiaBusiness.getTipologieIngredienti()){
            if(tp.getNome().equals(tipologia)){
                i.setTipologiaI(tp);
            }
        }
        for(Distributore d : EnteBusiness.getDistributori()){
            if(d.getNome().equals(distr)){
                i.setDistributore(d);
            }
        }
        ingredienteDAO.addIngrediente(i);
        session.removeFromSession(CatalogoBusiness.FILTRO_INGREDIENTI);
        session.removeFromSession(CatalogoBusiness.FILTRO_PRODOTTI);
        CatalogoBusiness.loadFilter();

    }
}
