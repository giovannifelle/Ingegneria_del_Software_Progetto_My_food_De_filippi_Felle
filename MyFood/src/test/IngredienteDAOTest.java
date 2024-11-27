package test;

import dao.ingrediente.DistributoreDAO;
import dao.ingrediente.IngredienteDAO;
import dao.ingrediente.ProduttoreDAO;
import dao.ingrediente.Tipologia_IngredienteDAO;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class IngredienteDAOTest {

    private final IngredienteDAO ingredienteDAO = IngredienteDAO.getInstance();
    private ArrayList<Ingrediente> ingredienti = new ArrayList<>();

    private Ingrediente ingrediente=new Ingrediente();

    @Before
    public void setUp() {
        DistributoreDAO distributoreDAO= DistributoreDAO.getInstance();
        ProduttoreDAO produttoreDAO= ProduttoreDAO.getInstance();
        Tipologia_IngredienteDAO tipologiaIngredienteDAO=Tipologia_IngredienteDAO.getInstance();
        ingrediente.setNome("Mozzarella");
        ingrediente.setProduttore(produttoreDAO.findProduttoreById(4));
        ingrediente.setDistributore(distributoreDAO.findDistributoreById(5));
        ingrediente.setTipologiaI(tipologiaIngredienteDAO.findTipologiaIngredienteById(1));
        ingrediente.setIdIngrediente(13);

    }

    @Test
    public void findIngredienteByIdTest() {
        ingrediente = ingredienteDAO.findIngredienteById(12);
        Assert.assertNotNull(ingrediente);
        System.out.println(ingrediente);
    }

    @Test
    public void findAllTest() {
        ingredienti = ingredienteDAO.findAll();
        Assert.assertNotNull(ingredienti);
        for (Ingrediente i : ingredienti)
            System.out.println(i + "\n");

    }

    @Test
    public void findIngredientiOfPTest() {
        ingredienti = ingredienteDAO.findIngrendientiOfP(20);
        Assert.assertNotNull(ingredienti);
        for (Ingrediente i : ingredienti)
            System.out.println(i + "\n");
    }

    @Test
    public void removeIngredienteTest() {
        int rows = ingredienteDAO.removeIngrediente(12);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }


    @Test
    public void addIngredienteTest() {
        int rows = ingredienteDAO.addIngrediente(ingrediente);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateIngredienteTest(){
        int rows= ingredienteDAO.updateIngrediente(ingrediente);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }
}
