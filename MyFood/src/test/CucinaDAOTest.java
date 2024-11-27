package test;

import dao.utente.CucinaDAO;
import model.utente.Cucina;
import model.utente.IUtente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CucinaDAOTest {

    private final CucinaDAO cucinaDAO = CucinaDAO.getInstance();

    private IUtente cucina = new Cucina();

    private Cucina c = new Cucina();

    private ArrayList<IUtente> cucine = new ArrayList<>();

    @Before
    public void setUp() {
        c.setEmail("cucina.prova@cucinadiprova.com");
        c.setPassword("cucina");
        c.setIdCucina(3);
        c.setSede("Prova");
    }

    @Test
    public void findCucinaByIdTest() {
        cucina = cucinaDAO.findById(1);
        System.out.println(cucina);
        Assert.assertNotNull(cucina);
    }

    @Test
    public void findAllTest() {
        cucine = cucinaDAO.findAll();
        for (IUtente i : cucine) {
            System.out.println(i + "\n");
        }
        Assert.assertNotNull(cucine);
    }

    @Test
    public void createCucinaTest() {
        int rows = cucinaDAO.createCucina(c);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }
    @Test
    public void updateCucinaTest(){
        int rows= cucinaDAO.updateCucina(c);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void removeCucinaTest(){
        int rows= cucinaDAO.removeCucina(3);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }
    
    @Test
    public void findByEmail(){
        cucina= cucinaDAO.findByEmail("cucina.prova@cucinadiprova.com");
        System.out.println(cucina);
        Assert.assertNotNull(cucina);
    }
}
