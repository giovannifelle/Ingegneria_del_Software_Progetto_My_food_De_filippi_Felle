package dao.prodotto;

import com.mysql.cj.exceptions.CJException;
import dao.utente.CommentoDAO;
import dao.ingrediente.IngredienteDAO;
import dao.ingrediente.P_SingoloHasIngredienteDAO;
import dbInterface.command.DbOperationExecutor;
import dbInterface.command.IDbOperation;
import dbInterface.command.ReadOperation;
import dbInterface.command.WriteOperation;
import model.prodotto.composite.*;
import model.prodotto.factory.ProdottoFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoDAO {

    private static ProdottoDAO instance;

    //private static ResultSet rs;
    private static final DbOperationExecutor executor = new DbOperationExecutor();
    private static final MenuHasProdottoDAO mhpDAO = MenuHasProdottoDAO.getInstance();

    private static final P_SingoloHasIngredienteDAO pshiDAO = P_SingoloHasIngredienteDAO.getInstance();

    private static final ImmagineDAO immagineDAO = ImmagineDAO.getInstance();

    private ProdottoDAO() {
    }

    public static ProdottoDAO getInstance() {
        if (instance == null) {
            instance = new ProdottoDAO();
        }
        return instance;
    }

    public Integer findProdottoByNome(String nome) {
        String sql = "SELECT * FROM Prodotto WHERE Prodotto.nome='" + nome + "';";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                return rs.getInt("idProdotto");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Prodotto; ");
        }
        return null;
    }

    public IProdotto findProdottoById(Integer id) {
        IProdotto p = null;
        p = findProdottoSingoloById(id);
        if (p == null) {
            p = findMenuById(id);
        }
        return p;
    }

    public ArrayList<IProdotto> findAll() {
        ArrayList<IProdotto> catalogo = new ArrayList<>();
        IProdotto p;
        String sql = "SELECT * FROM Prodotto;";
        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                p = findProdottoById(rs.getInt("idProdotto"));
                catalogo.add(p);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Prodotto; ");
        }
        return catalogo;
    }

    public Prodotto_Singolo findProdottoSingoloById(Integer id) {
        Prodotto_Singolo p = null;
        Tipologia_ProdottoDAO tp = Tipologia_ProdottoDAO.getInstance();
        IngredienteDAO i = IngredienteDAO.getInstance();
        CommentoDAO comm = CommentoDAO.getInstance();
        ImmagineDAO imm = ImmagineDAO.getInstance();
        String sql = "SELECT * FROM Prodotto AS P " +
                "INNER JOIN P_Singolo AS Ps ON P.idProdotto = Ps.idP_Singolo " +
                "WHERE idProdotto=" + id + ";";
        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                p = (Prodotto_Singolo) ProdottoFactory.getFactory("prodotto_singolo").creaProdotto();
                p.setId(rs.getInt("idP_Singolo"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setDisponibilita(rs.getBoolean("disponibilita"));
                p.setTipologiaP(tp.findTipologiaProdottoById(rs.getInt("idTipologiaP")));
                p.setIngredienti(i.findIngrendientiOfP(p.getId()));
                p.setImmagini(imm.findImmaginiOfP(p.getId()));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Prodotto Singolo con questa specifica; ");
        }
        return p;
    }

    public Menu findMenuById(Integer id) {
        Menu m = null;
        ProdottoDAO p = getInstance();
        CommentoDAO comm = CommentoDAO.getInstance();
        ImmagineDAO imm = ImmagineDAO.getInstance();

        String sql = "SELECT * FROM Prodotto AS P " +
                "INNER JOIN Menu AS M ON P.idProdotto = M.idMenu " +
                "WHERE P.idProdotto = " + id + ";";
        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                m = (Menu) ProdottoFactory.getFactory("menu").creaProdotto();
                m.setId(rs.getInt("idMenu"));
                m.setNome(rs.getString("nome"));
                m.setSconto(rs.getFloat("sconto"));
                m.setProdotti(mhpDAO.findProdottiOfM(id));
                m.setImmagini(imm.findImmaginiOfP(m.getId()));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Menu con questo id: " + id);
        }
        return m;
    }

    public ArrayList<Prodotto_Singolo> findProdottiSingoliBy(String sql) {
        ArrayList<Prodotto_Singolo> prodotti = new ArrayList<>();
        Prodotto_Singolo p = null;
        Tipologia_ProdottoDAO tp = Tipologia_ProdottoDAO.getInstance();
        IngredienteDAO i = IngredienteDAO.getInstance();
        CommentoDAO comm = CommentoDAO.getInstance();
        ImmagineDAO imm = ImmagineDAO.getInstance();
        IDbOperation readOp = new ReadOperation(sql);

        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                p = (Prodotto_Singolo) ProdottoFactory.getFactory("prodotto_singolo").creaProdotto();
                p.setId(rs.getInt("idP_Singolo"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setDisponibilita(rs.getBoolean("disponibilita"));
                p.setTipologiaP(tp.findTipologiaProdottoById(rs.getInt("idTipologiaP")));
                p.setIngredienti(i.findIngrendientiOfP(p.getId()));
                p.setImmagini(imm.findImmaginiOfP(p.getId()));
                prodotti.add(p);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Prodotto Singolo con questa specifica; ");
        }
        return prodotti;
    }

    public ArrayList<Prodotto_Singolo> findProdottiSingoliByIngredienti(Integer id) {

        String sql = "SELECT * FROM P_Singolo_has_Ingrediente AS PhI " +
                "INNER JOIN P_Singolo AS Ps INNER JOIN Prodotto AS P ON PhI.idProdottoS = Ps.idP_Singolo AND P.idProdotto=Ps.idP_Singolo " +
                "WHERE PhI.idIngrediente = " + id + ";";

        return (findProdottiSingoliBy(sql));

    }


    public ArrayList<Prodotto_Singolo> findProdottiSingoliByTipologia(Integer id) {

        String sql = "SELECT * FROM Tipologia_Prodotto AS Tp " +
                "INNER JOIN P_Singolo AS Ps INNER JOIN Prodotto AS P ON Tp.idTipologiaP = Ps.idTipologiaP AND P.idProdotto=Ps.idP_Singolo " +
                "WHERE Tp.idTipologiaP = " + id + ";";

        return (findProdottiSingoliBy(sql));

    }

    public Integer editProdotto(String sql) {
        try {
            IDbOperation writeOp = new WriteOperation(sql);
            int rows = executor.executeOperation(writeOp).getRowsAffected();
            return rows;
        } catch (CJException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Prodotto");
        }
        return 0;
    }

    public int removeProdotto(Integer id) {
        String sql = "DELETE FROM Prodotto WHERE idProdotto=" + id + ";";
        return editProdotto(sql);
    }

    public int addProdotto(IProdotto p) {
        int rows = 0;
        String sql = "INSERT INTO Prodotto (idProdotto, nome) " +
                "VALUES (null, '" + p.getNome() + "');";
        rows = rows + editProdotto(sql);
        int idP = findProdottoByNome(p.getNome());
        if (p.getImmagini() != null) {
            rows = rows + immagineDAO.addImmagini(p.getImmagini(), idP);
        }
        if (p instanceof Prodotto_Singolo) {
            Prodotto_Singolo ps = (Prodotto_Singolo) p;
            sql = "INSERT INTO P_Singolo (idP_Singolo, prezzo, disponibilita, idTipologiaP) " +
                    "VALUES (" + idP + ", " + ps.getPrezzo() + ", " + ps.getDisponibilita() + ", " + ps.getTipologiaP().getIdTipologiaP() + ");";
            rows = rows + editProdotto(sql);
            rows = rows + pshiDAO.setIngredienti(ps.getIngredienti(), idP);

        }
        if (p instanceof Menu) {
            Menu m = (Menu) p;
            sql = "INSERT INTO Menu (idMenu, sconto) " +
                    "VALUES (" + idP + "," + m.getSconto() + ");";
            rows = rows + editProdotto(sql);
            rows = rows + mhpDAO.setProdottiOfMenu(m.getProdotti(), idP);
        }
        return rows;
    }

    public int updateProdotto(IProdotto p) {
        int rows = 0;
        String sql = "UPDATE Prodotto SET" +
                " nome = '" + p.getNome() +
                "' WHERE idProdotto= " + p.getId() + ";";
        rows = rows + editProdotto(sql);
        if (p instanceof Prodotto_Singolo) {
            Prodotto_Singolo ps = (Prodotto_Singolo) p;
            sql = "UPDATE P_Singolo SET" +
                    " prezzo=" + ps.getPrezzo() +
                    ", disponibilita=" + ps.getDisponibilita() +
                    ", idTipologiaP= " + ps.getTipologiaP().getIdTipologiaP() +
                    " WHERE idP_Singolo= " + ps.getId() + ";";
            rows = rows + editProdotto(sql);
            rows = rows + pshiDAO.updateIngredientiOfP(ps.getIngredienti(), ps.getId());
        }
        if (p instanceof Menu) {
            Menu m = (Menu) p;
            sql = "UPDATE Menu SET" +
                    " sconto=" + m.getSconto() +
                    " WHERE idMenu=" + m.getId() + ";";
            rows += editProdotto(sql);
            rows += mhpDAO.updateProdottiOfMenu(m.getProdotti(), m.getId());
        }
        return rows;
    }

    public ArrayList<IProdotto> findProdottiOfO(Integer id) {
        ArrayList<IProdotto> prodotti = new ArrayList<>();

        String sql = "SELECT * FROM ordine_has_prodotto AS OHP " +
                "WHERE OHP.idOrdine =" + id + ";";

        IDbOperation readOp = new ReadOperation(sql);
        try {
            ResultSet rs = executor.executeOperation(readOp).getResultSet();
            while (rs.next()) {
                prodotti.add(findProdottoById(rs.getInt("idProdotto")));
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Non trovo nessun Ordine con questo id: " + id);
        }
        return prodotti;
    }

}