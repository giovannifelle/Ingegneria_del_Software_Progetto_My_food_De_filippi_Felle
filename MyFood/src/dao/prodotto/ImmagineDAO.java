package dao.prodotto;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.Immagine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImmagineDAO {
    private static ImmagineDAO instance = new ImmagineDAO();
    private static DbOperationExecutor executor = new DbOperationExecutor();

    private static ResultSet rs;

    private ImmagineDAO() {
    }

    public static ImmagineDAO getInstance() {
        return instance;
    }

    public ArrayList<Immagine> findAll() {
        ArrayList<Immagine> immagini = new ArrayList<>();
        String sql = "SELECT * FROM Immagine;";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                immagini.add(findImmagineById(rs.getInt("idImmagine")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Immagine; ");
        }
        return immagini;
    }

    public Immagine findImmagineById(Integer id) {
        Immagine imm = null;
        ProdottoDAO p = ProdottoDAO.getInstance();

        String sql = "SELECT * FROM Immagine AS Imm " +
                "WHERE Imm.idImmagine = " + id + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                imm = new Immagine();
                imm.setIdImmagine(rs.getInt("idImmagine"));
                imm.setNomeFile(rs.getString("nomeFile"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Immagine con questo id: " + id);
        }
        return imm;
    }

    public ArrayList<Immagine> findImmaginiOfP(Integer idp) {
        ArrayList<Immagine> immagini = new ArrayList<>();

        String sql = "SELECT * FROM Immagine AS Imm " +
                "WHERE Imm.idProdotto = " + idp + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                immagini.add(findImmagineById(rs.getInt("idImmagine")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Immagine con questo id prodotto: " + idp);
        }
        return immagini;
    }

    public Integer editImmagine(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Immagine ");
        }
        return 0;
    }

    public int addImmagine(Immagine immagine, Integer id){
        String sql="INSERT INTO Immagine (idImmagine, nomeFile, idProdotto) " +
                "VALUES (null, '"+immagine.getNomeFile()+"', "+id+");";
        return editImmagine(sql);
    }
    public int addImmagini(ArrayList<Immagine> immagini, Integer id){
        int rows=0;
        for(Immagine i: immagini){
          rows=rows+ addImmagine(i, id);
        }
        return rows;
    }

    public int updateImmagini(ArrayList<Immagine> immagini, Integer id){
        int rows=0;
        rows= rows +removeImmagini(id);
        rows = rows + addImmagini(immagini, id);
        return rows;
    }

    public int removeImmagini(Integer id){
        String sql= "DELETE FROM Immagine WHERE idProdotto="+id+" ;";
        return editImmagine(sql);
    }
}
