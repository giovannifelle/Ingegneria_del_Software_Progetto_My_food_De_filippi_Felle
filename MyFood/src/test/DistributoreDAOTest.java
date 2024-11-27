package test;

import dao.ingrediente.DistributoreDAO;
import model.ente.Distributore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DistributoreDAOTest {

    private final DistributoreDAO distributoreDAO = DistributoreDAO.getInstance();
    private Integer id;

    private Distributore distributore=new Distributore();

    private ArrayList<Distributore> distributori = new ArrayList<>();

    @Before
    public void setUp() {
        distributore.setNome("Distributore di update");
        distributore.setPartitaIVA("D321scsa");
        distributore.setIdDistributore(3);
    }

    @Test
    public void findDistributoreByIdTest() {
        distributore = distributoreDAO.findDistributoreById(1);
        Assert.assertNotNull(distributore);
        System.out.println(distributore);
    }

    @Test
    public void findAllTest() {
        distributori = distributoreDAO.findAll();
        Assert.assertNotNull(distributori);
        for (Distributore i : distributori)
            System.out.println(i + "\n");

    }

    @Test
    public void removeDistributoreTest() {
        int rows = distributoreDAO.removeDistributore(4);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }


    @Test
    public void addDistributoreTest() {
        int rows = distributoreDAO.addDistributore(distributore);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateDistributoreTest(){
        int rows= distributoreDAO.updateDistributore(distributore);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

}
