package dao.utente;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.utente.Cliente;
import model.utente.IUtente;
import model.utente.factory.UtenteFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO implements IUtenteDAO {

    private static ClienteDAO instance = new ClienteDAO();
    private final DbOperationExecutor executor = new DbOperationExecutor();

    private ClienteDAO() {
    }

    public static ClienteDAO getInstance() {
        return instance;
    }

    @Override
    public IUtente findById(Integer id) {
        String sql = "SELECT * FROM Cliente AS C " +
                "WHERE C.idCliente = " + id + ";";
        return findBy(sql);
    }

    @Override
    public IUtente findByEmail(String email) {
        String sql = "SELECT * FROM Cliente AS C " +
                "WHERE C.email = '" + email + "';";
        return findBy(sql);
    }

    public IUtente findBy(String sql) {
        Cliente c = null;

        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                c= (Cliente) UtenteFactory.getFactory("cliente").creaUtente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNome(rs.getString("nome"));
                c.setCognome(rs.getString("cognome"));
                c.setEmail(rs.getString("email"));
                c.setPassword(rs.getString("password"));
                c.setDataNascita(rs.getDate("dataNascita").toLocalDate());
                c.setProfessione(rs.getString("professione"));
                c.setResidenza(rs.getString("residenza"));
                c.setTelefono(rs.getString("telefono"));
                c.setDataRegistrazione(rs.getDate("dataRegistrazione").toLocalDate());
                c.setDataUltimoAccesso(rs.getDate("dataUltimoAccesso").toLocalDate());
                c.setDisabilitato(rs.getBoolean("disabilitato"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Cliente;");
        }
        return c;
    }

    @Override
    public ArrayList<IUtente> findAll() {
        Cliente c=null;
        ArrayList<IUtente> clienti = new ArrayList<>();
        String sql = "SELECT * FROM Cliente;";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                c= (Cliente) UtenteFactory.getFactory("cliente").creaUtente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNome(rs.getString("nome"));
                c.setCognome(rs.getString("cognome"));
                c.setEmail(rs.getString("email"));
                c.setPassword(rs.getString("password"));
                c.setDataNascita(rs.getDate("dataNascita").toLocalDate());
                c.setProfessione(rs.getString("professione"));
                c.setResidenza(rs.getString("residenza"));
                c.setTelefono(rs.getString("telefono"));
                c.setDataRegistrazione(rs.getDate("dataRegistrazione").toLocalDate());
                c.setDataUltimoAccesso(rs.getDate("dataUltimoAccesso").toLocalDate());
                c.setDisabilitato(rs.getBoolean("disabilitato"));
                clienti.add(c);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Cliente");
        }
        return clienti;
    }

    @Override
    public boolean existUser(String query) {
        IDbOperation readOp = new ReadOperation(query);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            rs.next();
            if (rs.getRow() == 1) {
                int count = rs.getInt("count");
                return count == 1;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean exist(String email) {
        return existUser("SELECT COUNT(*) AS count FROM Cliente AS cl WHERE cl.email='" + email + "';");
    }

    @Override
    public boolean passwordOK(String email, String password) {
        return existUser("SELECT COUNT(*) AS count FROM Cliente AS cl WHERE cl.email='" + email + "' AND cl.password='" + password + "';");
    }

    public Integer editCliente(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Cliente");
        }
        return 0;
    }

    public int createCliente(Cliente cl) {
        String sql = "INSERT INTO Cliente (idCliente, nome, cognome, email, password, dataNascita, professione, residenza, telefono, dataRegistrazione, dataUltimoAccesso, disabilitato)" +
                " VALUES (null,'" + cl.getNome() + "', '" + cl.getCognome() + "', '" + cl.getEmail() + "', '" + cl.getPassword() + "', '" + cl.getDataNascita() + "', '" + cl.getProfessione() + "', '" +
                cl.getResidenza() + "', '" + cl.getTelefono() + "', '" + cl.getDataRegistrazione() + "', '" +
                cl.getDataUltimoAccesso() + "'," + cl.getDisabilitato() + ");";
        return editCliente(sql);
    }

    public int removeCliente(Integer id) {
        String sql = "DELETE FROM Cliente WHERE idCliente=" + id + ";";
        return editCliente(sql);
    }

    //Update del cliente senza modificare l'id e la data di registrazione.
    public int updateCliente(Cliente cl) {
        String sql = "UPDATE Cliente SET" +
                " nome='" + cl.getNome() +
                "', cognome='" + cl.getCognome() +
                "', email='" + cl.getEmail() +
                "', password='" + cl.getPassword() +
                "', dataNascita='" + cl.getDataNascita() +
                "', professione='" + cl.getProfessione() +
                "', residenza='" + cl.getResidenza() +
                "', telefono='" + cl.getTelefono() +
                "', dataUltimoAccesso='" + cl.getDataUltimoAccesso() +
                "', disabilitato=" + cl.getDisabilitato() +
                " WHERE idCliente=" + cl.getIdCliente() + ";";
        return editCliente(sql);
    }
}

