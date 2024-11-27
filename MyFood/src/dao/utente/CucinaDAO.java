package dao.utente;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.utente.Amministratore;
import model.utente.Cucina;
import model.utente.IUtente;
import model.utente.factory.UtenteFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CucinaDAO implements IUtenteDAO {

    private static CucinaDAO instance = new CucinaDAO();
    private final DbOperationExecutor executor = new DbOperationExecutor();

    private CucinaDAO() {
    }

    public static CucinaDAO getInstance() {
        return instance;
    }

    @Override
    public IUtente findById(Integer id) {
        Cucina cu = null;


        String sql = "SELECT * FROM Cucina AS Cu " +
                "WHERE Cu.idCucina = " + id + ";";

        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                cu = new Cucina();
                cu.setIdCucina(rs.getInt("idCucina"));
                cu.setEmail(rs.getString("email"));
                cu.setPassword(rs.getString("password"));
                cu.setSede(rs.getString("sede"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Cucina con questo id: " + id);
        }
        return cu;
    }

    @Override
    public IUtente findByEmail(String email) {
        Cucina cu = null;

        String sql = "SELECT * FROM Cucina AS Cu " +
                "WHERE Cu.email = '" + email + "';";

        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                cu= (Cucina) UtenteFactory.getFactory("cucina").creaUtente();
                cu.setIdCucina(rs.getInt("idCucina"));
                cu.setEmail(rs.getString("email"));
                cu.setPassword(rs.getString("password"));
                cu.setSede(rs.getString("sede"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Cucina con questa email: " + email);
        }
        return cu;
    }

    @Override
    public ArrayList<IUtente> findAll() {
        Cucina cu = null;
        ArrayList<IUtente> cucine = new ArrayList<>();
        String sql = "SELECT * FROM Cucina;";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                cu= (Cucina) UtenteFactory.getFactory("cucina").creaUtente();
                cu.setIdCucina(rs.getInt("idCucina"));
                cu.setEmail(rs.getString("email"));
                cu.setPassword(rs.getString("password"));
                cu.setSede(rs.getString("sede"));
                cucine.add(cu);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo Cucine ");
        }
        return cucine;
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
        return existUser("SELECT COUNT(*) AS count FROM Cucina AS c WHERE c.email='" + email + "';");
    }

    @Override
    public boolean passwordOK(String email, String password) {
        return existUser("SELECT COUNT(*) AS count FROM Cucina AS c WHERE c.email='" + email + "' AND c.password='" + password + "';");
    }

    public Integer editCucina(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Cucina");
        }
        return 0;
    }

    public int createCucina(Cucina c) {
        String sql = "INSERT INTO Cucina (idCucina, email, password) " +
                "VALUES (null,'" + c.getEmail() + "','" + c.getPassword() + "');";
        return editCucina(sql);
    }

    public int removeCucina(Integer id) {
        String sql = "DELETE FROM Cucina WHERE idCucina=" + id + ";";
        return editCucina(sql);
    }

    public int updateCucina(Cucina c) {
        String sql = "UPDATE Cucina SET" +
                " email='" + c.getEmail() +
                "', password='" + c.getPassword() +
                "', sede='" + c.getSede() +
                "' WHERE idCucina=" + c.getIdCucina() + ";";
        return editCucina(sql);
    }
}
