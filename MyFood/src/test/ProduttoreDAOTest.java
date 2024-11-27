package test;

import dao.ingrediente.ProduttoreDAO;
import model.ente.Produttore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ProduttoreDAOTest {

    private final ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
    private Produttore produttore = new Produttore();
    private ArrayList<Produttore> produttori = new ArrayList<>();

    @Before
    public void setUp() {
        produttore.setNome("Produttore update");
        produttore.setPartitaIVA("ABCD1234");
        produttore.setIdProduttore(7);
    }

    @Test
    public void findProduttoreByIdTest() {
        produttore = produttoreDAO.findProduttoreById(1);
        Assert.assertNotNull(produttore);
        System.out.println(produttore);
    }

    @Test
    public void findAllTest() {
        produttori = produttoreDAO.findAll();
        Assert.assertNotNull(produttori);
        for (Produttore i : produttori)
            System.out.println(i + "\n");

    }

    @Test
    public void removeProduttoreTest() {
        int rows = produttoreDAO.removeProduttore(7);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }


    @Test
    public void addProduttoreTest() {
        int rows = produttoreDAO.addProduttore(produttore);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateProduttoreTest() {
        int rows = produttoreDAO.updateProduttore(produttore);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

}
