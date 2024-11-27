package test;

import dao.utente.AmministratoreDAO;
import model.utente.Amministratore;
import model.utente.IUtente;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AmministratoreDAOTest {

    private final AmministratoreDAO amministratoreDAO = AmministratoreDAO.getInstance();

    private IUtente amministratore = new Amministratore();

    private ArrayList<IUtente> amministratori = new ArrayList<>();

    @Test
    public void findAmministratoreByIdTest() {
        amministratore = amministratoreDAO.findById(1);
        System.out.println(amministratore);
        Assert.assertNotNull(amministratore);
    }

    @Test
    public void findAllTest() {
        amministratori = amministratoreDAO.findAll();
        for (IUtente i : amministratori) {
            System.out.println(i + "\n");
        }
        Assert.assertNotNull(amministratori);
    }
}
