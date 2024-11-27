package model.utente;

public class Amministratore implements IUtente {
    private Integer idAmministratore;
    private String email;
    private String password;

    public Integer getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(Integer idAmministratore) {
        this.idAmministratore = idAmministratore;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Amministratore{" +
                "\nidAmministratore=" + idAmministratore +
                "\nemail='" + email + '\'' +
                "\npassword='" + password + '\'' +
                "\n}";
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
