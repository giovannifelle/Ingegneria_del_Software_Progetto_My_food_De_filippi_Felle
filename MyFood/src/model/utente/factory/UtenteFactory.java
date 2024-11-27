package model.utente.factory;

public class UtenteFactory{

    public static AbstractUtenteFactory getFactory(String tipo) {
        switch (tipo) {
            case "cliente":
                return new ClienteFactory();
            case "cucina":
                return new CucinaFactory();
        }
        return null;
    }
}