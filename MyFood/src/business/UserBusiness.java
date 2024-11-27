package business;

import business.result.UserResult;
import dao.utente.ClienteDAO;
import model.Ordine;
import model.prodotto.composite.IProdotto;
import model.utente.Cliente;

import java.util.ArrayList;

public class UserBusiness {
    private static UserBusiness instance;
    private static SessionBusiness session=SessionBusiness.getInstance();
    private Cliente cliente = (Cliente) LoginBusiness.getInSessionUser();

    private ClienteDAO clienteDAO=ClienteDAO.getInstance();

    private UserBusiness() {

    }

    public static UserBusiness getInstance() {
        if (instance == null) {
            instance = new UserBusiness();
        }
        return instance;
    }

    public UserResult changePassword(String oldPassword, String newPassword) {
        UserResult userResult = new UserResult();
        if(!oldPassword.equals(cliente.getPassword())){
            userResult.setUserResult(UserResult.USER_RESULT.WRONG_PASSWORD);
            userResult.setMessage("La vecchia password è errata!");
            return userResult;
        }
        if(newPassword.equals(oldPassword)){
            userResult.setUserResult(UserResult.USER_RESULT.OLD_PASSWORD);
            userResult.setMessage("La password è già in uso!");
            return userResult;
        }
        if(newPassword.equals("")){
            userResult.setUserResult(UserResult.USER_RESULT.EMPTY_NEW_PASSWORD);
            userResult.setMessage("Inserire la nuova password!");
            return userResult;
        }
        cliente.setPassword(newPassword);

        //aggiorno il ciente sul database
        clienteDAO.updateCliente(cliente);

        //aggiorno il cliente in sessione
        session.removeFromSession(LoginBusiness.LOGGED_IN_USER);
        session.putInSession(LoginBusiness.LOGGED_IN_USER, cliente);

        userResult.setUserResult(UserResult.USER_RESULT.UPDATED_PASSWORD);
        userResult.setMessage("Password aggiornata con successo!");
        return userResult;
    }

    public UserResult changeInfo(Cliente cliente) {
        UserResult userResult = new UserResult();
        if(cliente.getNome().equals("") || cliente.getCognome().equals("")){
            userResult.setUserResult(UserResult.USER_RESULT.NOT_ENOUGH_INFO);
            userResult.setMessage("Compilare i campi obbligatori!");
            return userResult;
        }

        //aggiorno il ciente sul database
        clienteDAO.updateCliente(cliente);

        //aggiorno il cliente in sessione
        session.removeFromSession(LoginBusiness.LOGGED_IN_USER);
        session.putInSession(LoginBusiness.LOGGED_IN_USER, cliente);

        userResult.setUserResult(UserResult.USER_RESULT.UPDATED_INFO);
        userResult.setMessage("Informazioni aggiornate con successo!");
        return userResult;
    }
    public static boolean isLogged(){
        return session.containsKey(LoginBusiness.LOGGED_IN_USER);
    }
    public static boolean isAdmin(){
        return session.containsKey(LoginBusiness.LOGGED_IN_ADMIN);
    }

    public static boolean isCucina(){
        return session.containsKey(LoginBusiness.LOGGED_IN_CUCINA);
    }

}