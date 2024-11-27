package dao.utente;

import com.mysql.cj.exceptions.CJException;
import dao.prodotto.ProdottoDAO;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.Commento;
import model.utente.Amministratore;
import model.utente.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentoDAO {

    private static CommentoDAO instance = new CommentoDAO();
    private final DbOperationExecutor executor = new DbOperationExecutor();

    private final ProdottoDAO p = ProdottoDAO.getInstance();
    private final ClienteDAO c = ClienteDAO.getInstance();
    private final AmministratoreDAO a = AmministratoreDAO.getInstance();

    private static ResultSet rs;

    private CommentoDAO() {
    }

    public static CommentoDAO getInstance() {
        return instance;
    }

    public ArrayList<Commento> findAll() {
        ArrayList<Commento> commenti = new ArrayList<>();
        String sql = "SELECT * FROM Commento;";
        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                commenti.add(findCommentoById(rs.getInt("idCommento")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Commento; ");
        }
        return commenti;
    }

    public Commento findCommentoById(Integer id) {
        Commento comm = null;

        String sql = "SELECT * FROM Commento AS C " +
                "WHERE C.idCommento = " + id + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                comm = new Commento();
                comm.setIdCommento(rs.getInt("idCommento"));
                comm.setIndiceGradimento(Commento.INDICE_GRADIMENTO.valueOf(rs.getString("indiceGradimento")));
                comm.setTesto(rs.getString("testo"));
                comm.setRisposta(rs.getString("risposta"));
                comm.setProdotto(p.findProdottoById(rs.getInt("idProdotto")));
                comm.setCliente((Cliente) c.findById(rs.getInt("idCliente")));
                comm.setAmministratore((Amministratore) a.findById(rs.getInt("idAmministratore")));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Commento con questo id: " + id);
        }
        return comm;
    }

    public ArrayList<Commento> findCommentiOfP(Integer idp) {
        ArrayList<Commento> commenti = new ArrayList<>();

        String sql = "SELECT * FROM Commento AS C " +
                "WHERE C.idProdotto = " + idp + ";";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                commenti.add(findCommentoById(rs.getInt("idCommento")));
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Commmento con questo id prodotto: " + idp);
        }
        return commenti;
    }

    public Integer editCommento(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun ");
        }
        return 0;
    }

    public int addCommento(Commento c) {
        String sql = "INSERT INTO Commento (idCommento, indiceGradimento, testo, risposta, idProdotto, idCliente, idAmministratore) " +
                "VALUES (null, '" + c.getIndiceGradimento() + "', '" + c.getTesto() + "', null, " + c.getProdotto().getId() + ", " + c.getCliente().getIdCliente() + ", null);";
        return editCommento(sql);
    }

    public int removeCommento(Integer id) {
        String sql = "DELETE FROM Commento WHERE idCommento=" + id + " ;";
        return editCommento(sql);
    }

    public int addRisposta(Commento c) {
        String sql = "UPDATE Commento SET" +
                " risposta='" + c.getRisposta() +
                "', idAmministratore=" + c.getAmministratore().getIdAmministratore() +
                " WHERE idCommento=" + c.getIdCommento() + ";";
        return editCommento(sql);
    }
}
