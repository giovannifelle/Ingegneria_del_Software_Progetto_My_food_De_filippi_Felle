package business;

import business.result.SigninResult;
import business.SessionBusiness;
import dao.utente.ClienteDAO;
import model.Ordine;
import model.ente.Produttore;
import model.prodotto.composite.IProdotto;
import model.utente.Cliente;
import model.utente.IUtente;
import model.utente.factory.UtenteFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteBusiness {

    private static ClienteBusiness instance;
    public final static String LISTA_CLIENTI="LISTA_CLIENTI";
    private static SessionBusiness session=SessionBusiness.getInstance();
    private static ClienteDAO clienteDAO=ClienteDAO.getInstance();

    private ClienteBusiness() {

    }

    public static ClienteBusiness getInstance() {
        if (instance == null) {
            instance = new ClienteBusiness();
        }
        return instance;
    }

    public static void removeCliente(String info) {
        String email=info.split(", ")[1];
        System.out.println(email);
        clienteDAO.removeCliente(((Cliente)clienteDAO.findByEmail(email)).getIdCliente());
        session.removeFromSession(LISTA_CLIENTI);
        loadClienti();

    }
    public static void disabilitaAbilitaCliente(String info){
        String email=info.split(", ")[1];
        Cliente cliente = ((Cliente)clienteDAO.findByEmail(email));
        if(cliente.getDisabilitato()) {
            cliente.setDisabilitato(false);
        }else{
            cliente.setDisabilitato(true);
        }
        clienteDAO.updateCliente(cliente);
        session.removeFromSession(LISTA_CLIENTI);
        loadClienti();
    }

    public SigninResult signin(String email, String password, String nome, String cognome, String residenza, String telefono, String professione, LocalDate data) {
        SigninResult signinResult = new SigninResult();

        if(email.equals("") || password.equals("") || nome.equals("")|| cognome.equals("")){
            signinResult.setSigninResult(SigninResult.SIGNIN_RESULT.EMPTY_BOX);
            signinResult.setMessage("Inserisci i campi obbligatori!");
            return signinResult;
        }
        if(clienteDAO.exist(email)){
            signinResult.setSigninResult(SigninResult.SIGNIN_RESULT.USER_EXIST);
            signinResult.setMessage("Esiste già un utente con questa email!");
            return signinResult;
        }
        Cliente cliente= (Cliente) UtenteFactory.getFactory("cliente").creaUtente();
        cliente.setNewCliente(nome, cognome, email, password, professione, residenza, telefono, data);
        clienteDAO.createCliente(cliente);
        signinResult.setSigninResult(SigninResult.SIGNIN_RESULT.SIGNIN_OK);
        signinResult.setMessage("Registrazione avvenuta con successo!");
        session.removeFromSession(LISTA_CLIENTI);
        loadClienti();
        return signinResult;

}
    public static void loadClienti(){
        ArrayList<IUtente> clienti= clienteDAO.findAll();
        session.putInSession(LISTA_CLIENTI,clienti);
    }

    public static ArrayList<IUtente> getClienti(){
        return (ArrayList<IUtente>)session.getFromSession(LISTA_CLIENTI);
    }

    public static ArrayList<String> getClientiInfo() {
        ArrayList<String> clienti = new ArrayList<>();

        for (IUtente c : getClienti()) {
            String infoCliente;
            if(((Cliente) c).getDisabilitato()){
              infoCliente=((Cliente) c).getNome()+" "+((Cliente) c).getCognome()+", "+c.getEmail()+", Disabilitato";
            }
            else{
                infoCliente=((Cliente) c).getNome()+" "+((Cliente) c).getCognome()+", "+c.getEmail()+", Abilitato";
            }
            clienti.add(infoCliente);
        }
        return clienti;
    }

    public static boolean acquistato() {
        ArrayList<Ordine> ordini=(ArrayList<Ordine>) session.getFromSession(OrdineBusiness.ORDINI_CLIENTE);
        IProdotto prodotto = (IProdotto) session.getFromSession(ProdottoBusiness.PRODOTTO);
        for(Ordine o : ordini){
            for(IProdotto p:o.getProdotti()){
                if(prodotto.getNome().equals(p.getNome())){
                    return true;
                }
            }
        }
        return false;
    }

}
