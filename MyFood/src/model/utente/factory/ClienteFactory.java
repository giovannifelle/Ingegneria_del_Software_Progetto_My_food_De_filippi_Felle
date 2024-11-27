package model.utente.factory;
import model.utente.Cliente;
import model.utente.IUtente;

public class ClienteFactory implements AbstractUtenteFactory {
    @Override
    public IUtente creaUtente() {
        return new Cliente();
    }
}