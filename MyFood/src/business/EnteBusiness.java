package business;

import dao.ingrediente.DistributoreDAO;

import dao.ingrediente.ProduttoreDAO;
import model.ente.Distributore;
import model.ente.Produttore;
import model.ente.factory.EnteFactory;
import model.prodotto.composite.Prodotto_Singolo;
import model.prodotto.factory.ProdottoFactory;

import java.util.ArrayList;


public class EnteBusiness {

    private static EnteBusiness instance;
    public static final String PRODUTTORI = "PRODUTTORI";
    public static final String DISTRIBUTORI = "DISTRIBUTORI";
    private static SessionBusiness session = SessionBusiness.getInstance();
    private static DistributoreDAO distributoreDAO = DistributoreDAO.getInstance();
    private static ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();

    private EnteBusiness() {

    }

    public static EnteBusiness getInstance() {
        if (instance == null) {
            instance = new EnteBusiness();
        }
        return instance;
    }

    public static void loadProduttoriDistributori() {
        ArrayList<Produttore> produttori = new ArrayList<>();
        ArrayList<Distributore> distributori = new ArrayList<>();

        produttori = produttoreDAO.findAll();
        distributori = distributoreDAO.findAll();

        session.putInSession(PRODUTTORI, produttori);
        session.putInSession(DISTRIBUTORI, distributori);

    }

    public static ArrayList<String> getProduttoriNames() {
        ArrayList<String> produttori = new ArrayList<>();
        for (Produttore p : getProduttori()) {
            produttori.add(p.getNome());
        }
        return produttori;
    }

    public static ArrayList<Produttore> getProduttori() {
        return (ArrayList<Produttore>) session.getFromSession(PRODUTTORI);
    }

    public static ArrayList<String> getDistributoriNames() {
        ArrayList<String> distributori = new ArrayList<>();
        for (Distributore d : getDistributori()) {
            distributori.add(d.getNome());
        }
        return distributori;
    }

    public static ArrayList<Distributore> getDistributori() {
        return (ArrayList<Distributore>) session.getFromSession(DISTRIBUTORI);
    }

    public static void addDistributore(String nome, String pIVA) {
        Distributore d = (Distributore) EnteFactory.getFactory("distributore").creaEnte();
        d.setNome(nome);
        d.setPartitaIVA(pIVA);
        distributoreDAO.addDistributore(d);
        session.removeFromSession(EnteBusiness.DISTRIBUTORI);
        session.removeFromSession(EnteBusiness.PRODUTTORI);
        loadProduttoriDistributori();
    }

    public static void addProduttore(String nome, String pIVA) {
        Produttore p = (Produttore) EnteFactory.getFactory("produttore").creaEnte();
        p.setNome(nome);
        p.setPartitaIVA(pIVA);
        produttoreDAO.addProduttore(p);
        session.removeFromSession(EnteBusiness.DISTRIBUTORI);
        session.removeFromSession(EnteBusiness.PRODUTTORI);
        loadProduttoriDistributori();
    }

}