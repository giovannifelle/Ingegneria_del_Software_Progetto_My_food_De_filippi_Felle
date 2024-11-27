package dao.ingrediente;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.ente.Distributore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DistributoreDAO {
    private static DistributoreDAO instance = new DistributoreDAO();
    private static DbOperationExecutor executor = new DbOperationExecutor();

    private DistributoreDAO() {
    }

    public static DistributoreDAO getInstance() {
        return instance;
    }

    public ArrayList<Distributore> findAll() {
        ArrayList<Distributore> distributori = new ArrayList<>();
        String sql = "SELECT * FROM Distributore;";
        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                distributori.add(findDistributoreById(rs.getInt("idDistributore")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Distributore; ");
        }
        return distributori;
    }

    public Distributore findDistributoreById(Integer id) {
        Distributore dis = null;
        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT * FROM Distributore AS D " +
                "WHERE idDistributore = " + id + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                dis = new Distributore();
                dis.setIdDistributore(rs.getInt("idDistributore"));
                dis.setNome(rs.getString("nome"));
                dis.setPartitaIVA(rs.getString("partitaIVA"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Distributore con questo id: " + id);
        }
        return dis;
    }

    public Integer editDistributore(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Distributore ");
        }
        return 0;
    }

    public int removeDistributore(Integer id) {
        String sql = "DELETE FROM Distributore WHERE idDistributore=" + id + ";";
        return editDistributore(sql);
    }

    public int addDistributore(Distributore d) {
        String sql = " INSERT INTO Distributore (idDistributore, nome, partitaIVA) " +
                "VALUES (null, '" + d.getNome() + "', '" + d.getPartitaIVA() + "');";
        return editDistributore(sql);
    }

    public int updateDistributore(Distributore d) {
        String sql = "UPDATE Distributore SET" +
                " nome = '" + d.getNome() +
                "', partitaIVA = '" + d.getPartitaIVA() +
                "' WHERE idDistributore= " + d.getId() + ";";
        return editDistributore(sql);
    }
}
