package dao.utente;

import model.utente.IUtente;

import java.util.ArrayList;

public interface IUtenteDAO {
    IUtente findById(Integer id);

    IUtente findByEmail(String email);

    ArrayList<IUtente> findAll();

    boolean exist(String email);

    boolean existUser(String sql);

    boolean passwordOK(String email, String password);
}
