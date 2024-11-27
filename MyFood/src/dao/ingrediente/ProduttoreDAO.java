package dao.ingrediente;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.ente.Produttore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProduttoreDAO {

    private static ProduttoreDAO instance = new ProduttoreDAO();

    private static DbOperationExecutor executor = new DbOperationExecutor();

    private ProduttoreDAO() {
    }

    public static ProduttoreDAO getInstance() {
        return instance;
    }


    public Produttore findProduttoreById(Integer id) {
        Produttore pr = null;

        String sql = "SELECT * FROM Produttore AS Pr " +
                "WHERE Pr.idProduttore = " + id + ";";

        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                pr = new Produttore();
                pr.setIdProduttore(rs.getInt("idProduttore"));
                pr.setNome(rs.getString("nome"));
                pr.setPartitaIVA(rs.getString("partitaIVA"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Produttore con questo id: " + id);
        }
        return pr;
    }

    public ArrayList<Produttore> findAll() {
        ArrayList<Produttore> produttori = new ArrayList<>();
        String sql = "SELECT * FROM Produttore;";
        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                produttori.add(findProduttoreById(rs.getInt("idProduttore")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Produttore; ");
        }
        return produttori;
    }

    public Integer editProduttore(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Produttore");
        }
        return 0;
    }

    public int removeProduttore(Integer id) {
        String sql = "DELETE FROM Produttore WHERE idProduttore=" + id + ";";
        return editProduttore(sql);
    }

    public int addProduttore(Produttore p) {
        String sql = " INSERT INTO Produttore (idProduttore, nome, partitaIVA) " +
                "VALUES (null, '" + p.getNome() + "', '" + p.getPartitaIVA() + "');";
        return editProduttore(sql);
    }

    public int updateProduttore(Produttore p) {
        String sql = "UPDATE Produttore SET" +
                " nome = '" + p.getNome() +
                "', partitaIVA = '" + p.getPartitaIVA() +
                "' WHERE idProduttore= " + p.getId() + ";";
        return editProduttore(sql);
    }
}
