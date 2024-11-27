package business;

import dao.utente.CommentoDAO;
import model.Commento;
import model.prodotto.composite.IProdotto;
import model.utente.Amministratore;
import model.utente.Cliente;
import model.utente.IUtente;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class CommentoBusiness {

    private static CommentoBusiness instance;
    public final static String LISTA_COMMENTI = "LISTA_COMMENTI";

    public final static String LISTA_COMMENTI_SENZA_RISPSOTA = "LISTA_COMMENTI_SENZA_RISPOSTA";
    private static SessionBusiness session = SessionBusiness.getInstance();
    private static CommentoDAO commentoDAO = CommentoDAO.getInstance();

    private CommentoBusiness() {

    }

    public static CommentoBusiness getInstance() {
        if (instance == null) {
            instance = new CommentoBusiness();
        }
        return instance;
    }

    public static void loadCommenti() {
        ArrayList<Commento> commenti = commentoDAO.findAll();
        session.putInSession(LISTA_COMMENTI, commenti);
        loadCommentiSenzaRisposta();
    }

    public static void loadCommentiSenzaRisposta() {
        ArrayList<Commento> commentiSenzaRisposta = new ArrayList<>();
        for (Commento c : ((ArrayList<Commento>) session.getFromSession(LISTA_COMMENTI))) {
            if (c.getRisposta() == null) {
                commentiSenzaRisposta.add(c);
            }
        }
        session.putInSession(LISTA_COMMENTI_SENZA_RISPSOTA, commentiSenzaRisposta);
    }

    public static void updateCommentiSenzaRisposta() {
        session.removeFromSession(LISTA_COMMENTI_SENZA_RISPSOTA);
        loadCommentiSenzaRisposta();
    }

    public static ArrayList<Commento> getCommentiSenzaRisposta() {
        return ((ArrayList<Commento>) session.getFromSession(LISTA_COMMENTI_SENZA_RISPSOTA));
    }

    public static ArrayList<Commento> getCommenti(){
        return ((ArrayList<Commento>) session.getFromSession(LISTA_COMMENTI));
    }

    public static ArrayList<String> getCommentiInfo() {
        ArrayList<String> commentiInfo = new ArrayList<>();
        ArrayList<Commento> commenti = getCommenti();
        for (Commento c : commenti) {
            String info;
            info = c.getIdCommento() + ", " + c.getCliente().getEmail() + ", " + c.getProdotto().getNome();
            commentiInfo.add(info);
        }
        return commentiInfo;
    }

    public static ArrayList<String> getCommentiInfoOfCliente() {
        ArrayList<String> commentiInfo = new ArrayList<>();
        ArrayList<Commento> commenti = getCommenti();
        for (Commento c : commenti) {
            if (c.getCliente().getIdCliente().equals(((Cliente) LoginBusiness.getInSessionUser()).getIdCliente())) {
                String info;
                info = c.getIdCommento() + ", " + c.getCliente().getEmail() + ", " + c.getProdotto().getNome();
                commentiInfo.add(info);
            }
        }
        return commentiInfo;
    }

    public static ArrayList<String> getCommentiSenzaRispostaInfo() {
        ArrayList<String> commentiInfo = new ArrayList<>();
        ArrayList<Commento> commenti = getCommentiSenzaRisposta();
        for (Commento c : commenti) {
            String info;
            info = c.getIdCommento() + ", " + c.getCliente().getEmail() + ", " + c.getProdotto().getNome();
            commentiInfo.add(info);
        }
        return commentiInfo;
    }
    public static Commento getCommentoFromInfo(String info) {
        String[] infos = info.split(", ");
        for (Commento c : (ArrayList<Commento>) session.getFromSession(LISTA_COMMENTI)) {
            if (c.getIdCommento().toString().equals(infos[0])) {
                return c;
            }
        }
        System.out.println("Nessun Commento trovato in getCommentoFromInfo");
        return null;
    }

    public static void rispondiCommento(Commento c, String risposta) {
        c.setRisposta(risposta);
        c.setAmministratore((Amministratore) session.getFromSession(LoginBusiness.LOGGED_IN_ADMIN));
        CommentoDAO.getInstance().addRisposta(c);
        session.removeFromSession(LISTA_COMMENTI);
        session.removeFromSession(LISTA_COMMENTI_SENZA_RISPSOTA);
        loadCommenti();
    }

    public static void addCommento(String testo, String indice) {
        Commento commento = new Commento();
        commento.setCliente((Cliente) session.getFromSession(LoginBusiness.LOGGED_IN_USER));
        commento.setTesto(testo);
        commento.setIndiceGradimento(Commento.INDICE_GRADIMENTO.valueOf(indice));
        commento.setProdotto((IProdotto) session.getFromSession(ProdottoBusiness.PRODOTTO));
        commentoDAO.addCommento(commento);
        session.removeFromSession(LISTA_COMMENTI);
        loadCommenti();
    }
}
