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

public class IngredienteDAO {
    private static IngredienteDAO instance = new IngredienteDAO();
    private static DbOperationExecutor executor = new DbOperationExecutor();

    private IngredienteDAO() {
    }

    public static IngredienteDAO getInstance() {
        return instance;
    }

    public Ingrediente findIngredienteById(Integer id) {
        Ingrediente i = null;
        ProduttoreDAO pr = ProduttoreDAO.getInstance();
        DistributoreDAO dis = DistributoreDAO.getInstance();
        Tipologia_IngredienteDAO ti = Tipologia_IngredienteDAO.getInstance();


        String sql = "SELECT * FROM Ingrediente AS I " +
                "WHERE I.idIngrediente = " + id + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                i = new Ingrediente();
                i.setIdIngrediente(rs.getInt("idIngrediente"));
                i.setNome(rs.getString("nome"));
                i.setProduttore(pr.findProduttoreById(rs.getInt("idProduttore")));
                i.setDistributore(dis.findDistributoreById(rs.getInt("idDistributore")));
                i.setTipologiaI(ti.findTipologiaIngredienteById(rs.getInt("idTipologiaI")));

            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ingrediente con questo id: " + id);
        }
        return i;
    }

    public ArrayList<Ingrediente> findIngrendientiOfP(Integer id) {
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();

        String sql = "SELECT * FROM P_Singolo_has_Ingrediente AS PSI " +
                "WHERE PSI.idProdottoS = " + id + ";";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                ingredienti.add(findIngredienteById(rs.getInt("idIngrediente")));
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ingrediente con questo id: " + id);
        }
        return ingredienti;
    }


    public ArrayList<Ingrediente> findAll() {
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();

        String sql = "SELECT * FROM Ingrediente";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                ingredienti.add(findIngredienteById(rs.getInt("idIngrediente")));
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ingrediente ");
        }
        return ingredienti;
    }

    public Integer editIngrediente(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ingrediente ");
        }
        return 0;
    }

    public int removeIngrediente(Integer id) {
        String sql = "DELETE FROM Ingrediente WHERE idIngrediente=" + id + ";";
        return editIngrediente(sql);
    }

    public int addIngrediente(Ingrediente i) {
        String sql = " INSERT INTO Ingrediente (idIngrediente, nome, idProduttore, idDistributore, idTipologiaI) " +
                "VALUES (null, '" + i.getNome() + "', " + i.getProduttore().getId() + ", " + i.getDistributore().getId() + ", " + i.getTipologiaI().getIdTipologiaI() + ");";
        return editIngrediente(sql);
    }

    public int updateIngrediente(Ingrediente i) {
        String sql = "UPDATE Ingrediente SET" +
                " nome = '" + i.getNome() +
                ", idProduttore=" + i.getProduttore().getId() +
                ", idDistributore=" + i.getDistributore().getId() +
                ", idTipologiaI=" + i.getTipologiaI().getIdTipologiaI() +
                " WHERE idIngrediente= " + i.getIdIngrediente() + ";";
        return editIngrediente(sql);
    }

}