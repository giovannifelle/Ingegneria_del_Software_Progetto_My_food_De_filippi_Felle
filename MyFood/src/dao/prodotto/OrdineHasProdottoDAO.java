package dao.prodotto;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.WriteOperation;
import model.prodotto.composite.IProdotto;

import java.util.ArrayList;

public class OrdineHasProdottoDAO {

    private static OrdineHasProdottoDAO instance = new OrdineHasProdottoDAO();
    private static DbOperationExecutor executor = new DbOperationExecutor();

    private OrdineHasProdottoDAO() {
    }

    public static OrdineHasProdottoDAO getInstance() {
        return instance;
    }


    public Integer editOrdineHasProdotto(String sql) {
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

    public int setProdotto(IProdotto prodotto, Integer id) {
        String sql = "INSERT INTO ordine_has_prodotto (idOrdine, idProdotto) " +
                "VALUES (" + id + ", " + prodotto.getId() + ");";
        return editOrdineHasProdotto(sql);
    }

    public int setProdotti(ArrayList<IProdotto> prodotti, Integer id) {
        int rows = 0;
        for (IProdotto p : prodotti) {
            rows = rows + setProdotto(p, id);
        }
        return rows;
    }

    //Update del prodotto rimuovendo i record e riaggiungendoli
    public int updateProdottoOfO(ArrayList<IProdotto> prodotti, Integer id) {
        int rows = 0;
        rows = rows + removeProdottiOfO(id);
        rows = rows + setProdotti(prodotti, id);
        return rows;
    }

    //Cancello i record e sfrutto questo metodo per l'uptdate
    public int removeProdottiOfO(Integer id) {
        String sql = "DELETE FROM Ordine_has_Prodotto WHERE idOrdine=" + id + " ;";
        return editOrdineHasProdotto(sql);
    }
}
