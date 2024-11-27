package business;

import java.util.HashMap;

public class SessionBusiness {

    private static SessionBusiness instance;

    public static SessionBusiness getInstance() {
        if (instance == null) {
            instance = new SessionBusiness();
        }
        return instance;
    }
    private SessionBusiness(){}

    private static HashMap<String, Object> session = new HashMap<>();

    public void putInSession(String key, Object object) {
        session.put(key, object);
    }

    public Object getFromSession(String key) {
        return session.get(key);
    }

    public void removeFromSession(String key) {
        session.remove(key);
    }

    public static void startSession(){
        CatalogoBusiness.loadFilter();
        CatalogoBusiness.loadProducts();
        EnteBusiness.loadProduttoriDistributori();
        TipologiaBusiness.loadTipologiaIngredienti();
        ClienteBusiness.loadClienti();
        CommentoBusiness.loadCommenti();
        OrdineBusiness.loadOrdini();
    }

    public boolean containsKey(String key){
        return session.containsKey(key);
    }
}
