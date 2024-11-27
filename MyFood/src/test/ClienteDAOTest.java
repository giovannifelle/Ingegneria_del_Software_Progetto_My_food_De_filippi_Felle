package test;

import dao.utente.ClienteDAO;
import model.utente.Cliente;
import model.utente.IUtente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteDAOTest {

    private final ClienteDAO clienteDAO = ClienteDAO.getInstance();

    private IUtente cliente = new Cliente();

    private Cliente cl = new Cliente();

    private ArrayList<IUtente> clienti = new ArrayList<>();

    @Before
    public void setUp() {
        cl.setNome("Edoardo");
        cl.setCognome("De Filippi");
        cl.setEmail("Edoardo.def@gmail.com");
        cl.setPassword("edoPasswords123");
        cl.setDataNascita(LocalDate.of(2002, 12, 30));
        cl.setProfessione("Ingegnere");
        cl.setResidenza("LE");
        cl.setTelefono("3471421456");
        cl.setDataRegistrazione(LocalDate.now());
        cl.setDataUltimoAccesso(LocalDate.now());
        cl.setDisabilitato(false);
        cl.setIdCliente(4);
    }

    @Test
    public void findClienteByIdTest() {
        cliente = clienteDAO.findById(1);
        System.out.println(cliente);
        Assert.assertNotNull(cliente);
    }
    @Test
    public void findClienteByEmail(){
        cliente=clienteDAO.findByEmail("paolo.rossi@gmail.com");
        System.out.println(cliente);
        Assert.assertNotNull(cliente);
    }

    @Test
    public void findAllTest() {
        clienti = clienteDAO.findAll();
        for (IUtente i : clienti) {
            System.out.println(i + "\n");
        }
        Assert.assertNotNull(clienti);
    }

    @Test
    public void createClienteTest() {
        int rows = clienteDAO.createCliente(cl);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }
    @Test
    public void updateClienteTest(){
        int rows= clienteDAO.updateCliente(cl);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }

    @Test
    public void removeClienteTest(){
        int rows= clienteDAO.removeCliente(22);
        System.out.println(rows);
        Assert.assertNotNull(rows);
    }
}
