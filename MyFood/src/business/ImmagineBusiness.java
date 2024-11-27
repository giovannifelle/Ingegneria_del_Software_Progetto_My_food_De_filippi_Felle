package business;

import dao.prodotto.ImmagineDAO;
import model.Immagine;

public class ImmagineBusiness {

    private static ImmagineBusiness instance;
    private static ImmagineDAO immagineDAO=ImmagineDAO.getInstance();

    private ImmagineBusiness() {
    }

    public static ImmagineBusiness getInstance() {
        if (instance == null) {
            instance = new ImmagineBusiness();
        }
        return instance;
    }


}
