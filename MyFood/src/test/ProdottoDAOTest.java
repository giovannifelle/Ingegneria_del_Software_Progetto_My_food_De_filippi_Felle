package test;

import dao.ingrediente.IngredienteDAO;
import dao.prodotto.ProdottoDAO;
import dao.prodotto.Tipologia_ProdottoDAO;
import model.*;
import org.junit.*;
import model.prodotto.composite.*;

import java.util.ArrayList;

public class ProdottoDAOTest {

    private final ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();

    private final IngredienteDAO ingredienteDAO= IngredienteDAO.getInstance();

    private final Tipologia_ProdottoDAO tipologia_ProdottoDAO= Tipologia_ProdottoDAO.getInstance();

    private IProdotto prodotto;
    private Prodotto_Singolo prodotto_s=new Prodotto_Singolo();

    private ArrayList<Ingrediente> ingredienti = new ArrayList<>();
    private Ingrediente in1, in2, in3;


    private Immagine imm=new Immagine();
    private ArrayList<Immagine> immagini=new ArrayList<>();

    private Menu menu=new Menu();
    private ArrayList<IProdotto> catalogo=new ArrayList<>();
    private ArrayList<Prodotto_Singolo> catalogoSel;


    @Before
    public void setUp() {
        in1=ingredienteDAO.findIngredienteById(3);
        in2= ingredienteDAO.findIngredienteById(2);
        in3=ingredienteDAO.findIngredienteById(5);
        ingredienti.add(in1);
        ingredienti.add(in2);
        ingredienti.add(in3);
        prodotto_s.setIngredienti(ingredienti);
        prodotto_s.setNome("Panino di prova");
        prodotto_s.setPrezzo(5f);
        prodotto_s.setDisponibilita(true);
        prodotto_s.setTipologiaP(tipologia_ProdottoDAO.findTipologiaProdottoById(1));
        imm.setNomeFile("immagine di prova");
        immagini.add(imm);
        prodotto_s.setImmagini(immagini);

        menu.setNome("Menu di prova");
        menu.setSconto(0.2f);
        menu.setImmagini(immagini);
        catalogo.add(prodottoDAO.findProdottoById(1));
        catalogo.add(prodottoDAO.findProdottoById(2));
        catalogo.add(prodottoDAO.findProdottoById(4));
        menu.setProdotti(catalogo);


        prodotto_s.setId(14);
        menu.setId(15);
    }

    @Test
    public void findProdottoByIdTest() {
        /*prodotto_s = prodottoDAO.findProdottoSingoloById(1);
        System.out.println(prodotto_s);
        Assert.assertNotNull(prodotto_s);*/

       /* menu = prodottoDAO.findMenuById(28);
        System.out.println(menu);
        Assert.assertNotNull(menu);*/
        prodotto = prodottoDAO.findProdottoById(1);
        System.out.println(prodotto);
        Assert.assertNotNull(prodotto);
    }

    @Test
    public void findAllTest() {
        catalogo = prodottoDAO.findAll();
        Assert.assertNotNull(catalogo);
        for (IProdotto i : catalogo) {
            System.out.println(i + "\n");
        }
    }

    @Test
    public void findProdottiSingoliByIngredientiTest() {
        catalogoSel = prodottoDAO.findProdottiSingoliByIngredienti(1);
        Assert.assertNotNull(catalogoSel);
        for (IProdotto i : catalogoSel) {
            System.out.println(i + "\n");
        }
    }

    @Test
    public void findProdottiSingoliByTipologiaTest() {
        catalogoSel = prodottoDAO.findProdottiSingoliByTipologia(1);
        Assert.assertNotNull(catalogoSel);
        for (IProdotto i : catalogoSel) {
            System.out.println(i + "\n");
        }
    }

    @Test
    public void removeProdottoTest() {
        int rows= prodottoDAO.removeProdotto(15);
        System.out.println("Rows affected: "+rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void addProdottoSingoloTest(){
        int rows= prodottoDAO.addProdotto(prodotto_s);
        System.out.println("Rows affected: "+rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void addMenuTest(){
        int rows= prodottoDAO.addProdotto(menu);
        System.out.println("Rows affected: "+rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateProdottoSingoloTest(){
        int rows=prodottoDAO.updateProdotto(prodotto_s);
        System.out.println("Rows affected: "+rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void updateMenuTest(){
        int rows=prodottoDAO.updateProdotto(menu);
        System.out.println("Rows affected: "+rows);
        Assert.assertNotNull(rows);
    }

}
