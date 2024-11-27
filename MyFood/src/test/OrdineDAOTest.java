package test;

import dao.utente.OrdineDAO;
import model.Ordine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class OrdineDAOTest {
    private final OrdineDAO ordineDAO= OrdineDAO.getInstance();
    private Ordine ordine=new Ordine();
    private ArrayList<Ordine> ordini=new ArrayList<>();

    @Before
    public void setUp(){
        ordine=ordineDAO.findOrdineById(1);
    }

    @Test
    public void findOrdineByIdTest(){
        ordine=ordineDAO.findOrdineById(1);
        System.out.println(ordine);
        Assert.assertNotNull(ordine);
    }

    @Test
    public void findAllTest(){
        ordini=ordineDAO.findAll();
        System.out.println(ordini);
        Assert.assertNotNull(ordini);
    }

    @Test
    public void addOrdineTest(){
        int rows= ordineDAO.addOrdine(ordine);
        System.out.println("Rows affected: "+rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateStatoOrdineTest(){
        ordine.setStato(Ordine.STATO.PRONTO);
        int rows= ordineDAO.updateStatoOrdine(ordine);
        System.out.println("Rows affected: "+rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateSalvatoOrdineTest(){
        ordine.setSalvato(true);
        int rows= ordineDAO.updateSalvatoOrdine(ordine);
        System.out.println("Rows affected: "+rows);
        Assert.assertNotNull(rows);
    }
}
