package dao.utente;

import com.mysql.cj.exceptions.CJException;
import dao.prodotto.OrdineHasProdottoDAO;
import dao.prodotto.ProdottoDAO;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.Ordine;
import model.utente.Cliente;
import model.utente.Cucina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdineDAO {

    private static OrdineDAO instance = new OrdineDAO();
    private final DbOperationExecutor executor = new DbOperationExecutor();

    private final OrdineHasProdottoDAO ordineHasProdottoDAO=OrdineHasProdottoDAO.getInstance();

    private OrdineDAO() {
    }

    public static OrdineDAO getInstance() {
        return instance;
    }


    public Ordine findOrdineById(Integer id) {
        String sql = "SELECT * FROM Ordine AS O " +
                "WHERE O.idOrdine = " + id + ";";
        return findOrdineBy(sql);
    }

    public Ordine findOrdineBy(String sql) {
        Ordine o = null;

        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                o = new Ordine();
                o.setIdOrdine(rs.getInt("idOrdine"));
                o.setData(rs.getDate("data").toLocalDate());
                o.setImporto(rs.getFloat("importo"));
                o.setStato(Ordine.STATO.valueOf(rs.getString("stato")));
                o.setSalvato(rs.getBoolean("salvato"));
                o.setCliente((Cliente) ClienteDAO.getInstance().findById(rs.getInt("idCliente")));
                o.setCucina((Cucina) CucinaDAO.getInstance().findById(rs.getInt("idCucina")));
                o.setProdotti(ProdottoDAO.getInstance().findProdottiOfO(o.getIdOrdine()));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ordine;");
        }
        return o;
    }

    public ArrayList<Ordine> findAll() {
        ArrayList<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT * FROM Ordine";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                ordini.add(findOrdineById(rs.getInt("idOrdine")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ordine; ");
        }
        return ordini;
    }


    public Integer editOrdine(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ordine ");
        }
        return 0;
    }

    private Integer ultimoIdOrdine(){
        String sql="SELECT * FROM myfood.ordine ORDER BY idOrdine DESC LIMIT 1;";
        return findOrdineBy(sql).getIdOrdine();
    }

    public int addOrdine(Ordine o) {
        String sql = "INSERT INTO Ordine (idOrdine, data, importo, stato, salvato, idCliente, idCucina) " +
                "VALUES (null, '" + o.getData() + "', " + o.getImporto() + ", '" + o.getStato() + "', " + o.getSalvato() + ", " + o.getCliente().getIdCliente() + ", " + o.getCucina().getIdCucina() + ");";
        Integer ret= editOrdine(sql);
        ordineHasProdottoDAO.setProdotti(o.getProdotti(), ultimoIdOrdine());
        return ret;
    }

    public int removeOrdine(Integer id) {
        String sql = "DELETE FROM Ordine WHERE idOrdine=" + id + " ;";
        return editOrdine(sql);
    }

    public int updateStatoOrdine(Ordine o) {
        String sql = "UPDATE Ordine SET" +
                " stato='" + o.getStato() +
                "' WHERE idOrdine=" + o.getIdOrdine() + ";";
        return editOrdine(sql);
    }

    public int updateSalvatoOrdine(Ordine o) {
        String sql = "UPDATE Ordine SET" +
                " salvato=" + o.getSalvato() +
                " WHERE idOrdine=" + o.getIdOrdine() + ";";
        return editOrdine(sql);
    }

}
