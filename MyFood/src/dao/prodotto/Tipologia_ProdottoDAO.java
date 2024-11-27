package dao.prodotto;


import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.Tipologia_Prodotto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tipologia_ProdottoDAO {

    private static Tipologia_ProdottoDAO instance = new Tipologia_ProdottoDAO();
    private static DbOperationExecutor executor = new DbOperationExecutor();

    private Tipologia_ProdottoDAO() {
    }

    public static Tipologia_ProdottoDAO getInstance() {
        return instance;
    }

    public ArrayList<Tipologia_Prodotto> findAll() {
        ArrayList<Tipologia_Prodotto> tipologiaProdotti = new ArrayList<>();
        String sql = "SELECT * FROM Tipologia_Prodotto;";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                tipologiaProdotti.add(findTipologiaProdottoById(rs.getInt("idTipologiaP")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna TipologiaP; ");
        }
        return tipologiaProdotti;
    }

    public Tipologia_Prodotto findTipologiaProdottoById(Integer id) {
        Tipologia_Prodotto tp = null;

        String sql = "SELECT * FROM Tipologia_Prodotto AS Tp " +
                "WHERE Tp.idTipologiaP = " + id + ";";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                tp = new Tipologia_Prodotto();
                tp.setIdTipologiaP(rs.getInt("idTipologiaP"));
                tp.setNome(rs.getString("nome"));

            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna TipologiaP con questo id: " + id);
        }
        return tp;
    }

    public Integer editTipologiaProdotto(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Tipologia Prodotto");
        }
        return 0;
    }

    public int removeTipologiaProdotto(Integer id) {
        String sql = "DELETE FROM Tipologia_Prodotto WHERE idTipologiaP=" + id + ";";
        return editTipologiaProdotto(sql);
    }

    public int addTipologiaProdotto(Tipologia_Prodotto tpp) {
        String sql = " INSERT INTO Tipologia_Prodotto (idTipologiaP, nome) " +
                "VALUES (null, '" + tpp.getNome() + "');";
        return editTipologiaProdotto(sql);
    }

    public int updateTipologiaProdotto(Tipologia_Prodotto tpp) {
        String sql = "UPDATE Tipologia_Prodotto SET" +
                " nome = '" + tpp.getNome() +
                "' WHERE idTipologiaP= " + tpp.getIdTipologiaP() + ";";
        return editTipologiaProdotto(sql);
    }

}