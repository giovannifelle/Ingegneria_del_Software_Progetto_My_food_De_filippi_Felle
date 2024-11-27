package business;

import dao.utente.CucinaDAO;
import model.utente.Cucina;
import model.utente.IUtente;

import java.util.ArrayList;

public class CucinaBusiness {

    private static CucinaDAO cucinaDAO = CucinaDAO.getInstance();


    public static ArrayList<String> getSedi() {
        ArrayList<String> sedi = new ArrayList<>();
        ArrayList<IUtente> cucine = cucinaDAO.findAll();
        for (IUtente c : cucine) {
            sedi.add(((Cucina) c).getSede());
        }
        return sedi;
    }

    public static Cucina getCucinaBySede(String sede) {
        ArrayList<IUtente> cucine = cucinaDAO.findAll();
        for (IUtente c : cucine) {
            if (((Cucina) c).getSede().equals(sede)) {
                return (Cucina) c;
            }
        }
        return null;
    }
}