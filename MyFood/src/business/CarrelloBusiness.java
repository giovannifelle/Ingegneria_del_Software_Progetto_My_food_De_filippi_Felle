package business;

import model.Ordine;
import model.prodotto.composite.IProdotto;

import java.util.ArrayList;

public class CarrelloBusiness {

    private static CarrelloBusiness instance;

    public final static String CARRELLO = "CARRELLO";
    private static SessionBusiness session = SessionBusiness.getInstance();

    private CarrelloBusiness() {

    }

    public static CarrelloBusiness getInstance() {
        if (instance == null) {
            instance = new CarrelloBusiness();
        }
        return instance;
    }

    public static void addProdotto(IProdotto p) {
        ArrayList<IProdotto> carrello = new ArrayList<>();
        if (!session.containsKey(CARRELLO)) {
            carrello.add(p);
            session.putInSession(CARRELLO, carrello);
        } else {
            carrello = getCarrello();
            carrello.add(p);
            session.removeFromSession(CARRELLO);
            session.putInSession(CARRELLO, carrello);
        }
    }

    public static void removeProdotto(IProdotto p) {
        ArrayList<IProdotto> carrello = new ArrayList<>();
        carrello = getCarrello();
        carrello.remove(p);
        session.removeFromSession(CARRELLO);
        session.putInSession(CARRELLO, carrello);
    }

    public static IProdotto findProdottoByNome(String nome) {
        for (IProdotto p : getCarrello()) {
            if (p.getNome().equals(nome)) {
                return p;
            }

        }
        return null;
    }

    public static ArrayList<IProdotto> getCarrello() {
        return (ArrayList<IProdotto>) session.getFromSession(CARRELLO);
    }

    public static void stampaCarrello() {
        for (IProdotto p : getCarrello()) {
            System.out.println(p.getNome() + "    " + p.getPrezzo() + "\n");
        }
    }

    public static void svuotaCarrello() {
        session.removeFromSession(CARRELLO);
    }

    public static boolean isNull() {
        if (!session.containsKey(CARRELLO) || getCarrello().isEmpty()) {
            return true;
        }
        return false;
    }

    public static Float prezzoFinale() {
        Float sum = 0f;
        for (IProdotto p : getCarrello()) {
            sum += p.getPrezzo();
        }
        return Math.round(sum * 100.0f) / 100.0f;
    }

    public static void riempiCarrelloDaOrdine(Ordine o){
        for(IProdotto p : o.getProdotti()){
            addProdotto(p);
        }
    }
}
