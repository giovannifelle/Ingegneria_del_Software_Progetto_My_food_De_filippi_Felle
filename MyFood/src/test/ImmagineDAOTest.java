package test;

import dao.prodotto.ImmagineDAO;
import model.Immagine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ImmagineDAOTest {
    private final ImmagineDAO immagineDAO = ImmagineDAO.getInstance();
    private Integer id;

    private Immagine immagine;

    private ArrayList<Immagine> immagini;

    @Before
    public void setUp() {
        id = 1;

    }

    @Test
    public void findImmagineByIdTest() {
        immagine = immagineDAO.findImmagineById(7);
        System.out.println(immagine);
        Assert.assertNotNull(immagine);
    }

    @Test
    public void findAllTest() {
        immagini = immagineDAO.findAll();
        Assert.assertNotNull(immagini);
        for (Immagine i : immagini)
            System.out.println(i + "\n");

    }

}
