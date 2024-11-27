package test;

import dao.prodotto.Tipologia_ProdottoDAO;
import model.Tipologia_Prodotto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class Tipologia_ProdottoDAOTest {

    private final Tipologia_ProdottoDAO tipologiaProdottoDAO = Tipologia_ProdottoDAO.getInstance();
    private Tipologia_Prodotto tipologiaProdotto = new Tipologia_Prodotto();
    private ArrayList<Tipologia_Prodotto> tipologiaProdotti;

    @Before
    public void setUp() {
        tipologiaProdotto.setIdTipologiaP(6);
        tipologiaProdotto.setNome("piadina");
    }

    @Test
    public void findTipologiaProdottoByIdTest() {
        tipologiaProdotto = tipologiaProdottoDAO.findTipologiaProdottoById(1);
        Assert.assertNotNull(tipologiaProdotto);
        System.out.println(tipologiaProdotto);
    }

    @Test
    public void findAllTest() {
        tipologiaProdotti = tipologiaProdottoDAO.findAll();
        for (Tipologia_Prodotto i : tipologiaProdotti)
            System.out.println(i + "\n");
        Assert.assertNotNull(tipologiaProdotti);
    }

    @Test
    public void removeTipologiaProdottoTest() {
        int rows = tipologiaProdottoDAO.removeTipologiaProdotto(6);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }


    @Test
    public void addTipologiaProdottoTest() {
        int rows = tipologiaProdottoDAO.addTipologiaProdotto(tipologiaProdotto);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateTipologiaProdottoTest() {
        int rows = tipologiaProdottoDAO.updateTipologiaProdotto(tipologiaProdotto);
        System.out.println("Rows affected: " + rows);
        Assert.assertNotNull(rows);
    }

}
