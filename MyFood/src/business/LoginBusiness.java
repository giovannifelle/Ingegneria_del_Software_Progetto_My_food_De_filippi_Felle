package business;

import business.result.LoginResult;
import dao.utente.AmministratoreDAO;
import dao.utente.ClienteDAO;
import dao.utente.CucinaDAO;
import dao.utente.IUtenteDAO;
import model.utente.Amministratore;
import model.utente.Cliente;
import model.utente.Cucina;
import model.utente.IUtente;

import java.time.LocalDate;

public class LoginBusiness {

    private static LoginBusiness instance;

    private static SessionBusiness session=SessionBusiness.getInstance();
    public static final String LOGGED_IN_USER = "LOGGED_IN_USER";
    public static final String LOGGED_IN_ADMIN="LOGGED_IN_ADMIN";
    public static final String LOGGED_IN_CUCINA="LOGGED_IN_CUCINA";

    public static IUtente getInSessionUser(){
        return (IUtente) session.getFromSession(LOGGED_IN_USER);
    }

    public static Amministratore getInSessionAdmin(){
        return (Amministratore) session.getFromSession(LOGGED_IN_ADMIN);
    }

    private LoginBusiness() {

    }

    public static LoginBusiness getInstance() {
        if (instance == null) {
            instance = new LoginBusiness();
        }
        return instance;
    }

    public LoginResult login(String email, String password, String tipo) {
        LoginResult loginResult = new LoginResult();
        IUtenteDAO utenteDAO;
        switch (tipo) {
            case "Cliente":
                utenteDAO = ClienteDAO.getInstance();
                if (!utenteDAO.exist(email)) {
                    loginResult.setLoginResult(LoginResult.LOGIN_RESULT.USER_DOESNT_EXIST);
                    loginResult.setMessage("Non esiste nessun cliente con questa email");
                    break;
                }
                if (!utenteDAO.passwordOK(email, password)) {
                    loginResult.setLoginResult(LoginResult.LOGIN_RESULT.WRONG_PASSWORD);
                    loginResult.setMessage("Password errata");
                    break;
                }
                Cliente cliente = (Cliente) utenteDAO.findByEmail(email);
                if (cliente.getDisabilitato()) {
                    loginResult.setLoginResult(LoginResult.LOGIN_RESULT.USER_BLOCKED);
                    loginResult.setMessage("Questo utente è stato disattivato");
                    break;
                }
                loginResult.setLoginResult(LoginResult.LOGIN_RESULT.LOGIN_OK);
                loginResult.setMessage("Benvenuto " + cliente.getNome() + "!");

                //metto il cliente in sessione
                cliente.setDataUltimoAccesso(LocalDate.now());
                ClienteDAO.getInstance().updateCliente(cliente);
                session.putInSession(LOGGED_IN_USER, cliente);
                OrdineBusiness.loadOrdiniCliente();

                break;
            case "Amministratore":
                utenteDAO = AmministratoreDAO.getInstance();
                if (!utenteDAO.passwordOK(email, password)) {
                    loginResult.setLoginResult(LoginResult.LOGIN_RESULT.USER_DOESNT_EXIST);
                    loginResult.setMessage("Credenziali errate");
                    break;
                }
                Amministratore amm = (Amministratore) utenteDAO.findByEmail(email);
                loginResult.setLoginResult(LoginResult.LOGIN_RESULT.LOGIN_OK);
                loginResult.setMessage("Benvenuto amministratore!");

                //metto l'amministratore in sessione
                session.putInSession(LOGGED_IN_ADMIN, amm);
                break;
            case "Cucina":
                utenteDAO = CucinaDAO.getInstance();
                if (!utenteDAO.passwordOK(email, password)) {
                    loginResult.setLoginResult(LoginResult.LOGIN_RESULT.USER_DOESNT_EXIST);
                    loginResult.setMessage("Credenziali errate");
                    break;
                }
                Cucina cucina = (Cucina) utenteDAO.findByEmail(email);
                loginResult.setLoginResult(LoginResult.LOGIN_RESULT.LOGIN_OK);
                loginResult.setMessage("Cucina operativa!");

                //metto la cucina in sessione
                session.putInSession(LOGGED_IN_CUCINA, cucina);
                OrdineBusiness.loadOrdiniIncompletiCucina();
                break;
        }
        return loginResult;
    }
    public void logOut(){
        if(UserBusiness.isLogged()) {
            session.removeFromSession(LOGGED_IN_USER);
            session.removeFromSession(OrdineBusiness.ORDINI_CLIENTE);
            CarrelloBusiness.svuotaCarrello();
        }
        if(UserBusiness.isAdmin()){
            session.removeFromSession(LOGGED_IN_ADMIN);
        }
        if(UserBusiness.isCucina()){
            session.removeFromSession(LOGGED_IN_CUCINA);
            session.removeFromSession(OrdineBusiness.ORDINI_INCOMPLETI_CUCINA);
        }
    }

}
