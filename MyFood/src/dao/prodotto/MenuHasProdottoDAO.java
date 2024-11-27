package dao.prodotto;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.prodotto.composite.IProdotto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuHasProdottoDAO {
    private static MenuHasProdottoDAO instance;

    //private static ResultSet rs;
    private static DbOperationExecutor executor = new DbOperationExecutor();

    private static ProdottoDAO prodottoDAO= ProdottoDAO.getInstance();


    private MenuHasProdottoDAO() {
    }

    public static MenuHasProdottoDAO getInstance() {
        if (instance == null) {
            instance = new MenuHasProdottoDAO();
        }
        return instance;
    }

    public ArrayList<IProdotto> findProdottiOfM(Integer idm) {
        ArrayList<IProdotto> listap = new ArrayList<>();

        String sql = "SELECT * FROM Menu_has_Prodotto AS MhP " +
                "WHERE MhP.idMenu = " + idm + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                listap.add(prodottoDAO.findProdottoById(rs.getInt("idProdotto")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Prodotto con questo id menu: " + idm);
        }
        return listap;
    }

    public Integer editMenuHasProdotto(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Record");
        }
        return 0;
    }

    public int setProdottoOfMenu(IProdotto prodotto, Integer id) {
        String sql = "INSERT INTO Menu_has_Prodotto (idMenu, idProdotto) " +
                "VALUES (" + id + ", " + prodotto.getId() + ");";
        return editMenuHasProdotto(sql);
    }

    public int setProdottiOfMenu(ArrayList<IProdotto> prodotti, Integer id) {
        int rows = 0;
        for (IProdotto p : prodotti) {
            rows = rows + setProdottoOfMenu(p, id);
        }
        return rows;
    }

    public int removeProdottiOfMenu(Integer id){
        String sql= "DELETE FROM Menu_has_Prodotto WHERE idMenu="+id+" ;";
        return editMenuHasProdotto(sql);
    }

    public int updateProdottiOfMenu(ArrayList<IProdotto> prodotti, Integer id){
        int rows=0;
        rows= rows + removeProdottiOfMenu(id);
        rows = rows + setProdottiOfMenu(prodotti, id);
        return rows;
    }

}
