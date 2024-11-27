package model.utente.factory;
import model.utente.Cucina;
import model.utente.IUtente;

public class CucinaFactory implements AbstractUtenteFactory {
    @Override
    public IUtente creaUtente() {
        return new Cucina();
    }
}