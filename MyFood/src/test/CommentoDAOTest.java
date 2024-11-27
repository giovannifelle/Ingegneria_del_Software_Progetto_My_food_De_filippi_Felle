package test;

import dao.prodotto.ProdottoDAO;
import dao.utente.AmministratoreDAO;
import dao.utente.ClienteDAO;
import dao.utente.CommentoDAO;
import model.Commento;
import model.utente.Amministratore;
import model.utente.Cliente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CommentoDAOTest {

    private final CommentoDAO commentoDAO = CommentoDAO.getInstance();
    private final ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
    private final AmministratoreDAO amministratoreDAO = AmministratoreDAO.getInstance();
    private final ClienteDAO clienteDAO = ClienteDAO.getInstance();
    private Commento commento = new Commento();
    private ArrayList<Commento> commenti = new ArrayList<>();

    @Before
    public void setUp() {
        commento.setIdCommento(2);
        commento.setIndiceGradimento(Commento.INDICE_GRADIMENTO.QUATTRO);
        commento.setTesto("mi piace, molto buono");
        commento.setProdotto(prodottoDAO.findProdottoById(1));
        commento.setAmministratore((Amministratore) amministratoreDAO.findById(1));
        commento.setCliente((Cliente) clienteDAO.findById(1));
        commento.setRisposta("Grazie mille!");
    }

    @Test
    public void findCommentoByIdTest() {
        commento = commentoDAO.findCommentoById(1);
        Assert.assertNotNull(commento);
        System.out.println(commento);
    }

    @Test
    public void findAllTest() {
        commenti = commentoDAO.findAll();
        Assert.assertNotNull(commenti);
        for (Commento i : commenti)
            System.out.println(i + "\n");

    }

    @Test
    public void findCommentiOfPTest() {
        commenti = commentoDAO.findCommentiOfP(1);
        Assert.assertNotNull(commenti);
        for (Commento i : commenti)
            System.out.println(i + "\n");
    }

    @Test
    public void addCommentoTest() {
        int rows = commentoDAO.addCommento(commento);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void removeCommentoTest() {
        int rows = commentoDAO.removeCommento(2);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void addRispostaTest() {
        int rows = commentoDAO.addRisposta(commento);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }

}
