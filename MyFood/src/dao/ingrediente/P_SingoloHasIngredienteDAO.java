package dao.ingrediente;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.WriteOperation;
import model.Ingrediente;
import java.util.ArrayList;

public class P_SingoloHasIngredienteDAO {

    private static P_SingoloHasIngredienteDAO instance = new P_SingoloHasIngredienteDAO();
    private static DbOperationExecutor executor = new DbOperationExecutor();

    private P_SingoloHasIngredienteDAO() {
    }

    public static P_SingoloHasIngredienteDAO getInstance() {
        return instance;
    }

    public Integer editP_SingoloHasIngrediente(String sql) {
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

    public int setIngrediente(Ingrediente ingrediente, Integer id) {
        String sql = "INSERT INTO P_Singolo_has_Ingrediente (idProdottoS, idIngrediente) " +
                "VALUES (" + id + ", " + ingrediente.getIdIngrediente() + ");";
        return editP_SingoloHasIngrediente(sql);
    }

    public int setIngredienti(ArrayList<Ingrediente> ingredienti, Integer id) {
        int rows = 0;
        for (Ingrediente i : ingredienti) {
            rows = rows + setIngrediente(i, id);
        }
        return rows;
    }

    //Update dell'ingrediente rimuovendo i record e riaggiungendoli
    public int updateIngredientiOfP(ArrayList<Ingrediente> ingredienti, Integer id) {
        int rows = 0;
        rows = rows + removeIngredientiOfP(id);
        rows = rows + setIngredienti(ingredienti, id);
        return rows;
    }

    //Cancello i record e sfrutto questo metodo per l'uptdate
    public int removeIngredientiOfP(Integer id) {
        String sql = "DELETE FROM P_Singolo_has_Ingrediente WHERE idProdottoS=" + id + " ;";
        return editP_SingoloHasIngrediente(sql);
    }
}
