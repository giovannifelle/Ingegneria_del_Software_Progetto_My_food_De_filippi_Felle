package business;


import business.facade.PdfMailFacade;
import dao.utente.OrdineDAO;
import model.Ordine;
import model.prodotto.composite.IProdotto;
import model.utente.Cliente;
import model.utente.Cucina;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrdineBusiness {
    private static OrdineBusiness instance;
    private static OrdineDAO ordineDAO = OrdineDAO.getInstance();
    private static SessionBusiness session = SessionBusiness.getInstance();
    public static final String ORDINI = "ORDINI";
    public static final String ORDINI_CLIENTE = "ORDINI_CLIENTE";
    public static final String ORDINI_CLIENTE_SALVATI = "ORDINI_CLIENTE_SALVATI";
    public static final String ORDINI_INCOMPLETI = "ORDINI_INCOMPLETI";

    public static final String ORDINI_INCOMPLETI_CUCINA = "ORDINI_INCOMPLETI_CUCINA";

    private OrdineBusiness() {
    }

    public static OrdineBusiness getInstance() {
        if (instance == null) {
            instance = new OrdineBusiness();
        }
        return instance;
    }

    public static ArrayList<Ordine> getOrdini() {
        return (ArrayList<Ordine>) session.getFromSession(ORDINI);
    }

    public static ArrayList<Ordine> getOrdiniCliente() {
        return (ArrayList<Ordine>) session.getFromSession(ORDINI_CLIENTE);
    }

    public static ArrayList<Ordine> getOrdiniIncompleti() {
        return (ArrayList<Ordine>) session.getFromSession(ORDINI_INCOMPLETI);
    }

    public static void loadOrdini() {
        ArrayList<Ordine> ordini = new ArrayList<>();
        ordini = ordineDAO.findAll();
        session.putInSession(ORDINI, ordini);
        loadOrdiniIncompleti();
    }

    public static void loadOrdiniCliente() {
        Cliente cliente = (Cliente) session.getFromSession(LoginBusiness.LOGGED_IN_USER);
        ArrayList<Ordine> ordini = getOrdini();
        ArrayList<Ordine> ordiniCliente = new ArrayList<>();
        ArrayList<Ordine> ordiniSalvatiCliente = new ArrayList<>();
        for (Ordine o : ordini) {
            if (o.getCliente().getEmail().equals(cliente.getEmail())) {
                ordiniCliente.add(o);
                if (o.getSalvato()) {
                    ordiniSalvatiCliente.add(o);
                }
            }
        }
        session.putInSession(ORDINI_CLIENTE, ordiniCliente);
        session.putInSession(ORDINI_CLIENTE_SALVATI, ordiniSalvatiCliente);
    }

    public static void loadOrdiniIncompleti() {
        ArrayList<Ordine> ordini = getOrdini();
        ArrayList<Ordine> ordiniIncompleti = new ArrayList<>();
        for (Ordine o : ordini) {
            if (!o.getStato().equals(Ordine.STATO.PRONTO) && !o.getStato().equals(Ordine.STATO.ANNULLATO)) {
                ordiniIncompleti.add(o);
            }
        }
        session.putInSession(ORDINI_INCOMPLETI, ordiniIncompleti);
    }

    public static void loadOrdiniIncompletiCucina() {
        ArrayList<Ordine> ordini = getOrdiniIncompleti();
        ArrayList<Ordine> ordiniIncompletiCucina = new ArrayList<>();
        for (Ordine o : ordini) {
            if (o.getCucina().getSede().equals(((Cucina) session.getFromSession(LoginBusiness.LOGGED_IN_CUCINA)).getSede())) {
                ordiniIncompletiCucina.add(o);
            }
        }
        session.putInSession(ORDINI_INCOMPLETI_CUCINA, ordiniIncompletiCucina);
    }

    public static ArrayList<String> getOrdiniInfo() {
        ArrayList<Ordine> ordini = (ArrayList<Ordine>) session.getFromSession(ORDINI_CLIENTE);
        ArrayList<String> ordiniInfo = new ArrayList<>();
        for (Ordine o : ordini) {
            String info = "";
            info += o.getIdOrdine() + ", " + o.getData().toString() + ", " + o.getStato();
            ordiniInfo.add(info);
        }
        return ordiniInfo;
    }

    public static ArrayList<String> getOrdiniSalvatiInfo() {
        ArrayList<Ordine> ordini = (ArrayList<Ordine>) session.getFromSession(ORDINI_CLIENTE_SALVATI);
        ArrayList<String> ordiniInfo = new ArrayList<>();
        for (Ordine o : ordini) {
            String info = "";
            info += o.getIdOrdine() + ", " + o.getData().toString() + ", " + o.getStato();
            ordiniInfo.add(info);
        }
        return ordiniInfo;
    }

    public static ArrayList<String> getOrdiniIncompletiCucinaInfo() {
        ArrayList<Ordine> ordini = (ArrayList<Ordine>) session.getFromSession(ORDINI_INCOMPLETI_CUCINA);
        ArrayList<String> ordiniInfo = new ArrayList<>();
        for (Ordine o : ordini) {
            String info = "";
            info += o.getIdOrdine() + ", " + o.getCliente().getEmail() + ", " + o.getData().toString() + ", " + o.getStato();
            ordiniInfo.add(info);
        }
        return ordiniInfo;
    }

    public static String getInfoOrdine(Ordine o) {
        String info = "<html>Numero Ordine: " + o.getIdOrdine() + ";<br>" +
                "Cliente: " + o.getCliente().getNome() + " " + o.getCliente().getCognome() + "   " + o.getCliente().getEmail() + ";<br>" +
                "Data: " + o.getData().toString() + ";<br>" +
                "Lista prodotti: <br><ul>";
        for (IProdotto p : o.getProdotti()) {
            info += "<li>" + p.getNome() + ";<br>";
        }
        info += "</ul>" +
                "Stato Ordine: " + o.getStato() +
                ";</html>";

        return info;
    }

    public static Ordine getOrdineCucinaFromInfo(String info) {
        String[] infos = info.split(", ");
        for (Ordine o : (ArrayList<Ordine>) session.getFromSession(ORDINI_INCOMPLETI_CUCINA)) {
            if (o.getIdOrdine().toString().equals(infos[0])) {
                return o;
            }
        }
        System.out.println("Nessun Ordine trovato in getOrdineFromInfo");
        return null;
    }

    public static Ordine getOrdineFromInfo(String info) {
        String[] infos = info.split(", ");
        for (Ordine o : (ArrayList<Ordine>) session.getFromSession(ORDINI_CLIENTE)) {
            if (o.getIdOrdine().toString().equals(infos[0])) {
                return o;
            }
        }
        System.out.println("Nessun Ordine trovato in getOrdineFromInfo");
        return null;
    }

    public static void updateStatoOrdine(Ordine o) {
        ordineDAO.updateStatoOrdine(o);
        session.removeFromSession(ORDINI);
        session.removeFromSession(ORDINI_INCOMPLETI);
        session.removeFromSession(ORDINI_INCOMPLETI_CUCINA);
        loadOrdini();
        loadOrdiniIncompletiCucina();
    }

    public static void updateSalvatoOrdine(Ordine o) {
        ordineDAO.updateSalvatoOrdine(o);
        reloadOrdiniCliente();
    }

    public static void reloadOrdiniCliente() {
        session.removeFromSession(ORDINI);
        session.removeFromSession(ORDINI_INCOMPLETI);
        session.removeFromSession(ORDINI_CLIENTE);
        session.removeFromSession(ORDINI_CLIENTE_SALVATI);
        loadOrdini();
        loadOrdiniCliente();
    }

    public static void reloadOrdini() {
        session.removeFromSession(ORDINI);
        session.removeFromSession(ORDINI_INCOMPLETI);
        loadOrdini();
    }

    public static void addOrdine(Cucina cucina) {
        Ordine ordine = new Ordine();
        ordine.setProdotti(CarrelloBusiness.getCarrello());
        ordine.setData(LocalDate.now());
        ordine.setCliente((Cliente) LoginBusiness.getInSessionUser());
        ordine.setImporto(CarrelloBusiness.prezzoFinale());
        ordine.setStato(Ordine.STATO.NON_PAGATO);
        ordine.setSalvato(false);
        ordine.setCucina(cucina);
        ordineDAO.addOrdine(ordine);
        reloadOrdiniCliente();
    }


    public static void inviaMailScontrino() {
        PdfMailFacade.inviaMailPdf(getOrdiniCliente(), (Cliente) LoginBusiness.getInSessionUser());
    }

    public static void inviaMailOrdinePronto(Ordine o){
        PdfMailFacade.inviaMailPronto(o.getCliente().getEmail());
    }
}

