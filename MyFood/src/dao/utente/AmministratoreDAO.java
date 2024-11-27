package dao.utente;

import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import model.utente.Amministratore;
import model.utente.Cliente;
import model.utente.IUtente;
import model.utente.factory.UtenteFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AmministratoreDAO implements IUtenteDAO {

    private static AmministratoreDAO instance = new AmministratoreDAO();

    private final DbOperationExecutor executor = new DbOperationExecutor();
    private static ResultSet rs;

    private AmministratoreDAO() {

    }

    public static AmministratoreDAO getInstance() {
        return instance;
    }

    @Override
    public IUtente findById(Integer id) {
        Amministratore a = null;
        String sql = "SELECT * FROM Amministratore AS A " +
                "WHERE A.idAmministratore = " + id + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                a = new Amministratore();
                a.setIdAmministratore(rs.getInt("idAmministratore"));
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Amministratore con questo id: " + id);
        }
        return a;
    }

    @Override
    public IUtente findByEmail(String email) {
        return findById(1);
    }

    @Override
    public ArrayList<IUtente> findAll() {
        Amministratore a = null;
        ArrayList<IUtente> amministratori = new ArrayList<>();
        String sql = "SELECT * FROM Amministratore;";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                a= (Amministratore) UtenteFactory.getFactory("amministratore").creaUtente();
                a.setIdAmministratore(rs.getInt("idAmministratore"));
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));
                amministratori.add(a);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ammnistratore");
        }
        return amministratori;
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
        return existUser("SELECT COUNT(*) AS count FROM Cliente AS user WHERE user.email='" + email + "';");
    }

    @Override
    public boolean passwordOK(String email, String password) {
        return existUser("SELECT COUNT(*) AS count FROM Amministratore AS am WHERE am.email='" + email + "' AND am.password='" + password + "';");
    }
}