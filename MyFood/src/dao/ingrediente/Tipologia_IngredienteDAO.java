package dao.ingrediente;

import com.mysql.cj.exceptions.CJException;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tipologia_IngredienteDAO {

    private static Tipologia_IngredienteDAO instance = new Tipologia_IngredienteDAO();
    private static DbOperationExecutor executor = new DbOperationExecutor();

    private Tipologia_IngredienteDAO() {
    }

    public static Tipologia_IngredienteDAO getInstance() {
        return instance;
    }

    public ArrayList<Tipologia_Ingrediente> findAll() {
        ArrayList<Tipologia_Ingrediente> tipologiaIngredienti = new ArrayList<>();
        String sql = "SELECT * FROM Tipologia_Ingrediente;";
        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                tipologiaIngredienti.add(findTipologiaIngredienteById(rs.getInt("idTipologiaI")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna TipologiaI;");
        }
        return tipologiaIngredienti;
    }

    public Tipologia_Ingrediente findTipologiaIngredienteById(Integer id) {
        Tipologia_Ingrediente ti = null;

        DbOperationExecutor executor = new DbOperationExecutor();

        String sql = "SELECT * FROM Tipologia_Ingrediente AS Ti " +
                "WHERE Ti.idTipologiaI = " + id + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                ti = new Tipologia_Ingrediente();
                ti.setIdTipologiaI(rs.getInt("idTipologiaI"));
                ti.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Tipologia_Ingrediente con questo id: " + id);
        }
        return ti;
    }


    public Integer editTipologiaIngrediente(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessuna Tipologia Ingrediente ");
        }
        return 0;
    }

    public int removeTipologiaIngrediente(Integer id) {
        String sql = "DELETE FROM Tipologia_Ingrediente WHERE idTipologiaI=" + id + ";";
        return editTipologiaIngrediente(sql);
    }

    public int addTipologiaIngrediente(Tipologia_Ingrediente tpi) {
        String sql = " INSERT INTO Tipologia_Ingrediente (idTipologiaI, nome) " +
                "VALUES (null, '" + tpi.getNome() + "');";
        return editTipologiaIngrediente(sql);
    }

    public int updateTipologiaIngrediente(Tipologia_Ingrediente tpi) {
        String sql = "UPDATE Tipologia_Ingrediente SET" +
                " nome = '" + tpi.getNome() +
                "' WHERE idTipologiaI= " + tpi.getIdTipologiaI() + ";";
        return editTipologiaIngrediente(sql);
    }
}