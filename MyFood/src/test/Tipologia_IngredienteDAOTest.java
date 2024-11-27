package test;

import dao.ingrediente.Tipologia_IngredienteDAO;
import model.Tipologia_Ingrediente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class Tipologia_IngredienteDAOTest {

    private final Tipologia_IngredienteDAO tipologiaIngredienteDAO = Tipologia_IngredienteDAO.getInstance();
    private Integer id;

    private Tipologia_Ingrediente tipologiaIngrediente=new Tipologia_Ingrediente();
    private ArrayList<Tipologia_Ingrediente> tipologiaIngredienti;

    @Before
    public void setUp() {
        tipologiaIngrediente.setIdTipologiaI(4);
        tipologiaIngrediente.setNome("latticini");

    }

    @Test
    public void findTipologiaIngredienteByIdTest() {
        tipologiaIngrediente = tipologiaIngredienteDAO.findTipologiaIngredienteById(1);
        Assert.assertNotNull(tipologiaIngrediente);
        System.out.println(tipologiaIngrediente);
    }

    @Test
    public void findAllTest() {
        tipologiaIngredienti = tipologiaIngredienteDAO.findAll();
        for (Tipologia_Ingrediente i : tipologiaIngredienti)
            System.out.println(i + "\n");
        Assert.assertNotNull(tipologiaIngredienti);
    }

    @Test
    public void removeTipologiaIngredienteTest() {
        int rows = tipologiaIngredienteDAO.removeTipologiaIngrediente(4);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }


    @Test
    public void addTipologiaIngredienteTest() {
        int rows = tipologiaIngredienteDAO.addTipologiaIngrediente(tipologiaIngrediente);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateTipologiaIngredienteTest(){
        int rows= tipologiaIngredienteDAO.updateTipologiaIngrediente(tipologiaIngrediente);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

}
